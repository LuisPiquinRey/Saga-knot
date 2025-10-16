package com.luispiquinrey.user.Service;

import java.io.PrintWriter;
import java.util.Optional;

import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.CommandScan;
import org.springframework.shell.command.annotation.EnableCommand;
import org.springframework.shell.command.annotation.ExceptionResolver;
import org.springframework.shell.command.annotation.ExitCode;
import org.springframework.shell.command.annotation.Option;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luispiquinrey.Error.CreationException;
import com.luispiquinrey.Error.DeleteException;
import com.luispiquinrey.Error.SearchException;
import com.luispiquinrey.Error.UpdateException;
import com.luispiquinrey.Service.CrudService;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Repository.ContactRepository;
import com.password4j.Password;

@Service
@Command
public class ServiceUser extends CrudService<Contact, Long> {

    @Autowired
    public ServiceUser(ContactRepository contactRepository) {
        super(contactRepository, Contact.class);
    }

    @Override
    @Command(command = "find-target")
    public Optional<Contact> findTargetById(@Option(longNames = "target") Long idTarget) throws SearchException {
        return super.findTargetById(idTarget);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Contact updateTarget(Contact target) throws UpdateException {
        if (target.getPassword() != null && !target.getPassword().isEmpty()) {
            target.setPassword(Password.hash(target.getPassword())
                    .addRandomSalt(12)
                    .withScrypt()
                    .getResult());
        }
        return super.updateTarget(target);
    }

    @Transactional(rollbackFor = Exception.class)
    @Command(command = "update-contact-simple")
    public Contact updateContactSimple(
            @Option(longNames = "email") String email,
            @Option(longNames = "password") String password,
            @Option(longNames = "phone") String phoneNumber,
            @Option(longNames = "profile") String profileImage) throws UpdateException {

        if (email == null || email.isEmpty()) {
            throw new UpdateException("Email is required to identify the contact");
        }
        Contact existingContact = ((ContactRepository) repositoryGeneric)
                .findByEmail(email)
                .orElseThrow(() -> new UpdateException("Contact not found with email: " + email));

        if (password != null && !password.isEmpty()) {
            existingContact.setPassword(Password.hash(password)
                    .addRandomSalt(12)
                    .withScrypt()
                    .getResult());
        }

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            existingContact.setPhoneNumber(phoneNumber);
        }

        if (profileImage != null && !profileImage.isEmpty()) {
            existingContact.setProfileImage(profileImage);
        }

        return super.updateTarget(existingContact);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Contact createTarget(Contact target) throws CreationException {
        target.setPassword(Password.hash(target.getPassword()).addRandomSalt(12).withScrypt().getResult());
        return super.createTarget(target);
    }

    @Transactional(rollbackFor = Exception.class)
    @Command(command = "create-contact-simple")
    public Contact createContactSimple(
            @Option(longNames = "email") String email,
            @Option(longNames = "password") String password,
            @Option(longNames = "phone") String phoneNumber)
            throws CreationException {

        if (email == null || email.isEmpty()) {
            throw new CreationException("Email is required");
        }

        if (password == null || password.isEmpty()) {
            throw new CreationException("Password is required");
        }

        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);
        contact.setPassword(Password.hash(password)
                .addRandomSalt(12)
                .withScrypt()
                .getResult());

        return super.createTarget(contact);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Command(command = "delete-target")
    public void deleteTarget(@Option(longNames = "target") Long idTarget) throws DeleteException {
        super.deleteTarget(idTarget);
    }

    @ExceptionResolver
    @ExitCode(code = 5)
    public void errorHandler(Exception e, Terminal terminal) {
        PrintWriter writer = terminal.writer();
        writer.println("❌ Error handled: " + e.getMessage());
        writer.println("👉 Please check your command or input.");
        writer.flush();
    }
}
