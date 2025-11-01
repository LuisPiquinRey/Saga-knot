package com.luispiquinrey.user.Repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.luispiquinrey.user.Containers.MysqlTestContainer;
import com.luispiquinrey.user.Entities.Contact;

@DataJpaTest
@Import(MysqlTestContainer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    private List<Contact> contacts;

    @BeforeEach
    void setUpData() {
        contacts = new ArrayList<>();

        Contact contact1 = new Contact.Builder()
                .username("juan123")
                .email("juan@example.com")
                .password("Password1!")
                .phoneNumber("+34123456789")
                .build();

        Contact contact2 = new Contact.Builder()
                .username("maria456")
                .email("maria@example.com")
                .password("Password2@")
                .phoneNumber("+34198765432")
                .build();

        Contact contact3 = new Contact.Builder()
                .username("pedro789")
                .email("pedro@example.com")
                .password("Password3#")
                .build();

        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);

        contactRepository.saveAll(contacts);
    }

    @Test
    void testFindByUsername() {
        Optional<Contact> contact = contactRepository.findByUsername("juan123");

        assertTrue(contact.isPresent());
        assertEquals("juan@example.com", contact.get().getEmail());
    }

    @Test
    void testFindByEmail() {
        Optional<Contact> contact = contactRepository.findByEmail("maria@example.com");

        assertTrue(contact.isPresent());
        assertEquals("maria456", contact.get().getUsername());
    }

    @Test
    void testFindByUsernameOrEmail() {
        Optional<Contact> contact1 = contactRepository.findByUsernameOrEmail("pedro789", "noexiste@example.com");
        Optional<Contact> contact2 = contactRepository.findByUsernameOrEmail("noexiste", "juan@example.com");

        assertTrue(contact1.isPresent());
        assertTrue(contact2.isPresent());
        assertEquals("pedro789", contact1.get().getUsername());
        assertEquals("juan123", contact2.get().getUsername());
    }

    @Test
    void testExistsByUsername() {
        assertTrue(contactRepository.existsByUsername("maria456"));
        assertFalse(contactRepository.existsByUsername("noexiste"));
    }

    @Test
    void testExistsByEmail() {
        assertTrue(contactRepository.existsByEmail("juan@example.com"));
        assertFalse(contactRepository.existsByEmail("falso@example.com"));
    }

    @Test
    void testDeleteByUsername() {
        contactRepository.deleteByUsername("pedro789");

        Optional<Contact> deleted = contactRepository.findByUsername("pedro789");
        assertFalse(deleted.isPresent());
        assertEquals(2, contactRepository.count());
    }

    @Test
    void testDeleteByEmail() {
        contactRepository.deleteByEmail("maria@example.com");

        Optional<Contact> deleted = contactRepository.findByEmail("maria@example.com");
        assertFalse(deleted.isPresent());
        assertEquals(2, contactRepository.count());
    }
}
