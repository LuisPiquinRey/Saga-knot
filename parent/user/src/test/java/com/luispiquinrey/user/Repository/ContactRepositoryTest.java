package com.luispiquinrey.user.Repository;

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
public class ContactRepositoryTest {
    @Autowired
    private ContactRepository contactRepository;
    private List<Contact> contacts;

    @BeforeEach
    void setUpData(){
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
    void findByUsername(String username){
        assertTrue(contactRepository.findByUsername("juan123").isPresent());
    }
}
