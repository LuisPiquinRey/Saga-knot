package com.luispiquinrey.user.Service;

import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.luispiquinrey.common.Error.CreationException;
import com.luispiquinrey.common.Error.DeleteException;
import com.luispiquinrey.common.Error.UpdateException;
import com.luispiquinrey.common.Service.WrapperCrudServiceRedis;
import com.luispiquinrey.user.Entities.Address;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Error.SearchException;
import com.luispiquinrey.user.Repository.ContactRepository;

@Service
public class ContactService extends WrapperCrudServiceRedis<Contact, Long> implements IContactService {

    private final ContactRepository contactRepository;
    private static final String USERNAME_CACHE_PREFIX;
    private static final String EMAIL_CACHE_PREFIX;
    private final RabbitTemplate rabbitTemplate;
    private static final Keycloak keycloak;
    private static final RealmResource realmResource;
    private static final UsersResource usersResource;

    public ContactService(RedisTemplate<String, Contact> redisTemplate,
            ContactRepository contactRepository, RabbitTemplate rabbitTemplate) {
        super(redisTemplate, contactRepository, Contact.class);
        this.contactRepository = contactRepository;
        this.rabbitTemplate = rabbitTemplate;
    }
    static{
        USERNAME_CACHE_PREFIX = "contact:username:";
        EMAIL_CACHE_PREFIX = "contact:email:";
        keycloak = Keycloak.getInstance(
            "http://localhost:9030",
            "knot",
            "admin",
            "admin",
            "knot"
        );
        realmResource = keycloak.realm("master");
        usersResource = realmResource.users();
    }
    public Optional<Contact> findByUsername(String username) {
        String cacheKey = USERNAME_CACHE_PREFIX + username;
        Contact cached = super.redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return Optional.of(cached);
        }

        Optional<Contact> contact = contactRepository.findByUsername(username);
        contact.ifPresent(c -> super.redisTemplate.opsForValue().set(cacheKey, c));
        return contact;
    }

    public boolean existsByUsername(String username) {
        return contactRepository.existsByUsername(username);
    }

    @Transactional
    @Override
    public Contact createTarget(Contact target) throws CreationException {
        Contact created = super.createTarget(target);

        Long idContact = created.getIdContact();
        CorrelationData correlation = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(
                "exchange-order-user",
                "routing-key-order-user",
                idContact,
                correlation
        );

        super.redisTemplate.opsForValue().set(USERNAME_CACHE_PREFIX + created.getUsername(), created);

        try {
            UserRepresentation newUser = new UserRepresentation();
            newUser.setUsername(created.getUsername());
            newUser.setEnabled(true);
            newUser.setEmail(created.getEmail());

            Response response = usersResource.create(newUser);
            String keycloakId = CreatedResponseUtil.getCreatedId(response);

            created.setKeycloakId(keycloakId);
            contactRepository.save(created);

            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(created.getPassword());
            passwordCred.setTemporary(false);

            usersResource.get(keycloakId).resetPassword(passwordCred);

        } catch (Exception e) {
            throw new CreationException("Error creating user keycloak: " + e.getMessage());
        }

        return created;
    }

    @Transactional
    @Override
    public Contact updateTarget(Contact target) throws UpdateException {
        Contact updated = super.updateTarget(target);
        super.redisTemplate.opsForValue().set(USERNAME_CACHE_PREFIX + updated.getUsername(), updated);

        String keycloakId = updated.getKeycloakId();
        if (keycloakId == null) {
            throw new UpdateException("This user does not have a Keycloak ID associated.");
        }

        try {
            UserRepresentation userRep = usersResource.get(keycloakId).toRepresentation();
            userRep.setUsername(updated.getUsername());
            userRep.setEmail(updated.getEmail());
            userRep.setEnabled(true);

            usersResource.get(keycloakId).update(userRep);

        } catch (Exception e) {
            throw new UpdateException("Error updating user in Keycloak: " + e.getMessage());
        }

        return updated;
    }

    @Transactional
    @Override
    public void deleteTarget(Long id) throws DeleteException {
        Optional<Contact> contactOpt = super.findTargetById(id);

        super.deleteTarget(id);

        contactOpt.ifPresent(c -> {
            super.redisTemplate.delete(USERNAME_CACHE_PREFIX + c.getUsername());

            if (c.getKeycloakId() != null) {
                try {
                    usersResource.get(c.getKeycloakId()).remove();
                } catch (Exception ignored) {
                }
            }
        });
    }

    @Override
    public Optional<Contact> findByEmail(String email) {
        String cacheKey = EMAIL_CACHE_PREFIX + email;
        Contact cached = super.redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return Optional.of(cached);
        }

        Optional<Contact> contact = contactRepository.findByEmail(email);
        contact.ifPresent(c -> super.redisTemplate.opsForValue().set(cacheKey, c));
        return contact;
    }

    @Override
    public boolean existsByEmail(String email) {
        return contactRepository.existsByEmail(email);
    }

    @Override
    public void deleteByUsername(String username) {
        Optional<Contact> contactOpt = findByUsername(username);
        contactOpt.ifPresent(c -> {
            contactRepository.delete(c);
            super.redisTemplate.delete(USERNAME_CACHE_PREFIX + username);
            if (c.getEmail() != null) {
                super.redisTemplate.delete(EMAIL_CACHE_PREFIX + c.getEmail());
            }
        });
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<Contact> contactOpt = findByEmail(email);
        contactOpt.ifPresent(c -> {
            contactRepository.delete(c);
            if (c.getUsername() != null) {
                super.redisTemplate.delete(USERNAME_CACHE_PREFIX + c.getUsername());
            }
            super.redisTemplate.delete(EMAIL_CACHE_PREFIX + email);
        });
    }

    public Contact addAddressToUser(Long idContact, Address address) throws Exception {
        Contact existingContact = findTargetById(idContact)
                .orElseThrow(() -> new SearchException("Contact not found"));

        if (existingContact.getAddresses().stream()
                .anyMatch(a -> a.getIdAddress().equals(address.getIdAddress()))) {
            throw new SearchException("Address already present in user");
        }

        existingContact.addAddress(address);
        return updateTarget(existingContact);
    }
}
