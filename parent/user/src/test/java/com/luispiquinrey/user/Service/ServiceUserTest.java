package com.luispiquinrey.user.Service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import com.luispiquinrey.Error.CreationException;
import com.luispiquinrey.user.Containers.MysqlTestContainer;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Error.DeleteException;
import com.luispiquinrey.user.Error.SearchException;
import com.luispiquinrey.user.Error.UpdateException;
import com.luispiquinrey.user.Repository.ContactRepository;


@ExtendWith(MockitoExtension.class)
@Import(MysqlTestContainer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiceUserTest {
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService serviceUser;

    private Contact contact;

        @BeforeEach
    void setUp() {
        contact = new Contact.Builder()
                .id(1L)
                .username("john123")
                .email("john@example.com")
                .password("Password1!")
                .phoneNumber("+34123456789")
                .build();
    }

    @Test
    void testCreateTarget() throws CreationException {
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        Contact result = serviceUser.createTarget(contact);

        assertNotNull(result);
        assertNotNull(result.getPassword(), "Password should be hashed");
        verify(contactRepository, times(1)).save(contact);
    }

    @Test
    void testDeleteTarget() throws DeleteException {
        doNothing().when(contactRepository).deleteById(1L);

        serviceUser.deleteTarget(1L);

        verify(contactRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindTargetById() throws SearchException {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        Optional<Contact> found = serviceUser.findTargetById(1L);

        assertTrue(found.isPresent());
        assertEquals("john123", found.get().getUsername());
        verify(contactRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateTarget() throws UpdateException {
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        Contact updated = serviceUser.updateTarget(contact);

        assertNotNull(updated);
        assertNotNull(updated.getPassword(), "Password should be re-hashed");
        verify(contactRepository, times(1)).save(contact);
    }
}
