package com.luispiquinrey.user.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Error.DeleteException;
import com.luispiquinrey.user.Error.SearchException;
import com.luispiquinrey.user.Error.UpdateException;
import com.luispiquinrey.user.Repository.ContactRepository;


@Service
public class ImplServiceUser implements IServiceUser {

    private final ContactRepository contactRepository;
    private static final Logger logger = LoggerFactory.getLogger(ImplServiceUser.class);
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ImplServiceUser(ContactRepository contactRepository, PasswordEncoder passwordEncoder) {
        this.contactRepository = contactRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Contact> findByUsername(String username) throws SearchException {
        logger.debug("🔎 Finding contact by username: {}", username);
        if (!StringUtils.hasText(username)) {
            logger.warn("Username is empty or null ⚠️");
            throw SearchException.invalidParameter("username", username);
        }
        try {
            Optional<Contact> contact = contactRepository.findByUsername(username.trim());
            logger.debug("Contact found: {}", contact.isPresent());
            return contact;
        } catch (Exception e) {
            logger.error("❌ Error finding contact by username: {}", username, e);
            throw SearchException.databaseError("findByUsername", e);
        }
    }

    @Override
    public Optional<Contact> findByEmail(String email) throws SearchException {
        logger.debug("🔎 Finding contact by email: {}", email);
        if (!StringUtils.hasText(email)) {
            logger.warn("Email is null or empty ⚠️");
            throw SearchException.invalidParameter("email", email);
        }
        try {
            Optional<Contact> result = contactRepository.findByEmail(email.trim().toLowerCase());
            logger.debug("Contact found by email: {}", result.isPresent());
            return result;
        } catch (Exception e) {
            logger.error("❌ Error finding contact by email: {}", email, e);
            throw SearchException.databaseError("findByEmail", e);
        }
    }

    @Override
    public Optional<Contact> findByUsernameOrEmail(String username, String email) throws SearchException {
        logger.debug("🔎 Finding contact by username: {} or email: {}", username, email);

        if (!StringUtils.hasText(username) && !StringUtils.hasText(email)) {
            logger.warn("Both username and email are null or empty ⚠️");
            return Optional.empty();
        }

        try {
            String cleanUsername = StringUtils.hasText(username) ? username.trim() : "";
            String cleanEmail = StringUtils.hasText(email) ? email.trim().toLowerCase() : "";

            Optional<Contact> result = contactRepository.findByUsernameOrEmail(cleanUsername, cleanEmail);
            logger.debug("Contact found by username or email: {}", result.isPresent());
            return result;
        } catch (Exception e) {
            logger.error("❌ Error finding contact by username or email. Username: {}, Email: {}", username, email, e);
            throw SearchException.databaseError("findByUsernameOrEmail", e);
        }
    }

    @Override
    public Boolean existsByEmail(String email) throws SearchException {
        logger.debug("🔎 Checking if email exists: {}", email);
        if (!StringUtils.hasText(email)) {
            return false;
        }
        try {
            boolean exists = contactRepository.existsByEmail(email.trim().toLowerCase());
            logger.debug("Email exists: {}", exists);
            return exists;
        } catch (Exception e) {
            logger.error("❌ Error checking email existence: {}", email, e);
            throw SearchException.databaseError("existsByEmail", e);
        }
    }

    @Override
    public Boolean existsByUsername(String username) throws SearchException {
        logger.debug("🔎 Checking if username exists: {}", username);
        if (!StringUtils.hasText(username)) {
            return false;
        }
        try {
            boolean exists = contactRepository.existsByUsername(username.trim());
            logger.debug("Username exists: {}", exists);
            return exists;
        } catch (Exception e) {
            logger.error("❌ Error checking username existence: {}", username, e);
            throw SearchException.databaseError("existsByUsername", e);
        }
    }

    @Override
    public Page<Contact> findAll(Pageable pageable) {
        logger.debug("📄 Finding all contacts with pagination: {}", pageable);
        try {
            Page<Contact> result = contactRepository.findAll(pageable);
            logger.debug("Found {} contacts in page {} of {}", result.getNumberOfElements(), result.getNumber(), result.getTotalPages());
            return result;
        } catch (Exception e) {
            logger.error("❌ Error finding all contacts with pagination", e);
            throw SearchException.databaseError("findAll", e);
        }
    }

    public List<Contact> findAllBasic() {
        logger.debug("📄 Finding all contacts");
        try {
            List<Contact> contacts = contactRepository.findAll();
            logger.debug("Found {} contacts", contacts.size());
            return contacts;
        } catch (Exception e) {
            logger.error("❌ Error finding all basic contacts", e);
            throw SearchException.databaseError("findAllBasic", e);
        }
    }

    @Override
    public Contact save(Contact contact) {
        logger.info("💾 Saving new contact: {}", contact != null ? contact.getUsername() : "null");
        validateContactForSave(contact);

        try {
            contact.setUsername(contact.getUsername().trim());
            contact.setEmail(contact.getEmail().trim().toLowerCase());
            contact.setPassword(passwordEncoder.encode(contact.getPassword()));

            Contact savedContact = contactRepository.save(contact);
            logger.info("✅ Contact saved successfully with ID: {}", savedContact.getIdContact());
            return savedContact;
        } catch (Exception e) {
            logger.error("❌ Error saving contact: {}", contact.getUsername(), e);
            throw UpdateException.databaseError("save", e);
        }
    }

    @Override
    public Contact update(Contact contact) {
        logger.info("📝 Updating contact: {}", contact != null ? contact.getIdContact() : "null");
        validateContactForUpdate(contact);

        try {
            contact.setUsername(contact.getUsername().trim());
            contact.setEmail(contact.getEmail().trim().toLowerCase());

            Contact updatedContact = contactRepository.save(contact);
            logger.info("✅ Contact updated successfully: {}", updatedContact.getIdContact());
            return updatedContact;
        } catch (Exception e) {
            logger.error("❌ Error updating contact: {}", contact.getIdContact(), e);
            throw UpdateException.databaseError("update", e);
        }
    }

    @Override
    public void deleteByUsername(String username) {
        logger.info("🗑️ Deleting contact by username: {}", username);

        if (!StringUtils.hasText(username)) {
            throw DeleteException.notFound(username);
        }

        if (!existsByUsername(username)) {
            throw DeleteException.notFound(username);
        }

        try {
            contactRepository.deleteByUsername(username.trim());
            logger.info("✅ Contact deleted successfully by username: {}", username);
        } catch (Exception e) {
            logger.error("❌ Error deleting contact by username: {}", username, e);
            throw DeleteException.databaseError("deleteByUsername", e);
        }
    }

    @Override
    public void deleteByEmail(String email) {
        logger.info("🗑️ Deleting contact by email: {}", email);

        if (!StringUtils.hasText(email)) {
            throw DeleteException.notFoundByEmail(email);
        }

        if (!existsByEmail(email)) {
            throw DeleteException.notFoundByEmail(email);
        }

        try {
            contactRepository.deleteByEmail(email.trim().toLowerCase());
            logger.info("✅ Contact deleted successfully by email: {}", email);
        } catch (Exception e) {
            logger.error("❌ Error deleting contact by email: {}", email, e);
            throw DeleteException.databaseError("deleteByEmail", e);
        }
    }

    private void validateContactForSave(Contact contact) {
        if (contact == null) throw UpdateException.nullField("contact", null);
        if (!StringUtils.hasText(contact.getUsername())) throw UpdateException.nullField("username", null);
        if (!StringUtils.hasText(contact.getEmail())) throw UpdateException.nullField("email", null);
        if (existsByUsername(contact.getUsername())) throw UpdateException.duplicateUsername(contact.getUsername(), null);
        if (existsByEmail(contact.getEmail())) throw UpdateException.duplicateEmail(contact.getEmail(), null);
    }

    private void validateContactForUpdate(Contact contact) {
        if (contact == null) throw UpdateException.nullField("contact", null);
        if (contact.getIdContact() == null) throw UpdateException.nullField("idContact", null);
        if (!StringUtils.hasText(contact.getUsername())) throw UpdateException.nullField("username", contact.getIdContact());
        if (!StringUtils.hasText(contact.getEmail())) throw UpdateException.nullField("email", contact.getIdContact());
        if (!contactRepository.existsById(contact.getIdContact())) throw UpdateException.notFound(contact.getIdContact());

        Optional<Contact> existingByUsername = contactRepository.findByUsername(contact.getUsername());
        if (existingByUsername.isPresent() && !existingByUsername.get().getIdContact().equals(contact.getIdContact()))
            throw UpdateException.duplicateUsername(contact.getUsername(), contact.getIdContact());

        Optional<Contact> existingByEmail = contactRepository.findByEmail(contact.getEmail());
        if (existingByEmail.isPresent() && !existingByEmail.get().getIdContact().equals(contact.getIdContact()))
            throw UpdateException.duplicateEmail(contact.getEmail(), contact.getIdContact());
    }

    @Override
    public Boolean existsById(Long idContact) throws SearchException {
        logger.debug("🔍 Checking existence of contact by ID: {}", idContact);
        try {
            boolean exists = contactRepository.existsById(idContact);
            logger.debug("✅ Contact with ID '{}' exists: {}", idContact, exists);
            return exists;
        } catch (Exception e) {
            logger.error("❌ Error checking contact existence for ID: {}", idContact, e);
            throw new SearchException("Error checking existence of contact by ID: " + idContact, e);
        }
    }

    @Override
    public Optional<Contact> findByIdOptional(Long idContact) throws SearchException {
        logger.debug("🔎 Finding contact by ID: {}", idContact);
        if (idContact == null) throw SearchException.invalidParameter("idContact", null);
        try {
            Optional<Contact> contact = contactRepository.findById(idContact);
            logger.debug("Contact found by ID: {}", contact.isPresent());
            return contact;
        } catch (Exception e) {
            logger.error("❌ Error finding contact by ID: {}", idContact, e);
            throw SearchException.databaseError("findByIdOptional", e);
        }
    }
}
