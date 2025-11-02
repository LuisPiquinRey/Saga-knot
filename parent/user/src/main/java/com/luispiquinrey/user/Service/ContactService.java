package com.luispiquinrey.user.Service;

import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.luispiquinrey.Error.CreationException;
import com.luispiquinrey.Error.DeleteException;
import com.luispiquinrey.Error.UpdateException;
import com.luispiquinrey.Service.WrapperCrudServiceRedis;
import com.luispiquinrey.user.Entities.Address;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Error.SearchException;
import com.luispiquinrey.user.Event.AddressUpdatedEvent;
import com.luispiquinrey.user.Repository.ContactRepository;

@Service
public class ContactService extends WrapperCrudServiceRedis<Contact, Long> implements IContactService {

    private final ContactRepository contactRepository;
    private static final String USERNAME_CACHE_PREFIX = "contact:username:";
    private static final String EMAIL_CACHE_PREFIX = "contact:email:";

    public ContactService(RedisTemplate<String, Contact> redisTemplate,
            ContactRepository contactRepository) {
        super(redisTemplate, contactRepository, Contact.class);
        this.contactRepository = contactRepository;
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

    @Override
    public Contact createTarget(Contact target) throws CreationException {
        Contact created = super.createTarget(target);
        super.redisTemplate.opsForValue().set(USERNAME_CACHE_PREFIX + created.getUsername(), created);
        return created;
    }

    @Override
    public Contact updateTarget(Contact target) throws UpdateException {
        Contact updated = super.updateTarget(target);
        super.redisTemplate.opsForValue().set(USERNAME_CACHE_PREFIX + updated.getUsername(), updated);
        return updated;
    }

    @Override
    public void deleteTarget(Long id) throws DeleteException {
        Optional<Contact> contactOpt = super.findTargetById(id);
        super.deleteTarget(id);
        contactOpt.ifPresent(c -> super.redisTemplate.delete(USERNAME_CACHE_PREFIX + c.getUsername()));
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

    public void removeAddressFromUser(Long idContact, String idAddress) throws Exception {
        Contact existingContact = findTargetById(idContact)
                .orElseThrow(() -> new SearchException("Contact not found"));

        Address toRemove = existingContact.getAddresses().stream()
                .filter(a -> a.getIdAddress().equals(idAddress))
                .findFirst()
                .orElseThrow(() -> new SearchException("Address not associated with user"));

        existingContact.removeAddress(toRemove);
        updateTarget(existingContact);
    }

    public Contact updateAddressOfUser(Long idContact, AddressUpdatedEvent event) throws Exception {

        Contact existingContact = findTargetById(idContact)
                .orElseThrow(() -> new SearchException("Contact not found"));

        Address existingAddress = existingContact.getAddresses().stream()
                .filter(a -> a.getIdAddress().equals(event.getIdAddress()))
                .findFirst()
                .orElseThrow(() -> new SearchException("Address not associated with user"));

        existingAddress.setStreet(event.getStreet());
        existingAddress.setPostalCode(event.getPostalCode());
        existingAddress.setCity(event.getCity());
        existingAddress.setState(event.getState());
        existingAddress.setCountry(event.getCountry());

        return updateTarget(existingContact);
    }
}
