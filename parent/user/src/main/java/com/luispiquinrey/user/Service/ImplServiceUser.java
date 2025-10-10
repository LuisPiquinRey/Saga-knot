package com.luispiquinrey.user.Service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Error.DeleteException;
import com.luispiquinrey.user.Error.SearchException;
import com.luispiquinrey.user.Error.UpdateException;

@Service
public class ImplServiceUser implements IServiceUser{

    @Override
    public Optional<Contact> findByUsername(String username) throws SearchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Contact> findByEmail(String email) throws SearchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Contact> findByIdOptional(Long idContact) throws SearchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Contact> findByUsernameOrEmail(String username, String email) throws SearchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean existsByEmail(String email) throws SearchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean existsByUsername(String username) throws SearchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean existsById(Long idContact) throws SearchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Page<Contact> findAll(Pageable pageable) throws SearchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteByUsername(String username) throws DeleteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteByEmail(String email) throws DeleteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Contact save(Contact contact) throws UpdateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Contact update(Contact contact) throws UpdateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
