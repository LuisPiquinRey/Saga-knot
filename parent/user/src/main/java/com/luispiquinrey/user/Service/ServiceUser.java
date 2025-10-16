package com.luispiquinrey.user.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.CommandScan;
import org.springframework.shell.command.annotation.EnableCommand;
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
    public Optional<Contact> findTargetById(Long idTarget) throws SearchException {
        return super.findTargetById(idTarget);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Command(command = "update-contact")
    public Contact updateTarget(Contact target) throws UpdateException {
        if (target.getPassword() != null && !target.getPassword().isEmpty()) {
            target.setPassword(Password.hash(target.getPassword())
                    .addRandomSalt(12)
                    .withScrypt()
                    .getResult());
        }
        return super.updateTarget(target);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Command(command = "create-target")
    public Contact createTarget(Contact target) throws CreationException {
        target.setPassword(Password.hash(target.getPassword()).addRandomSalt(12).withScrypt().getResult());
        return super.createTarget(target);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTarget(Long idTarget) throws DeleteException {
        super.deleteTarget(idTarget);
    }
}
