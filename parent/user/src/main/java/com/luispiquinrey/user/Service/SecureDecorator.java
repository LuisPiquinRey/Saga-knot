package com.luispiquinrey.user.Service;

import com.luispiquinrey.common.Entities.BaseEntity;
import com.luispiquinrey.common.Utilities.BaseDecorator;
import com.luispiquinrey.common.Utilities.IDataService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import java.util.Optional;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.representations.idm.UserRepresentation;

import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.common.Error.CreationException;
import com.luispiquinrey.common.Error.UpdateException;
import com.luispiquinrey.common.Error.DeleteException;

public class SecureDecorator<T extends BaseEntity<ID>, ID> extends BaseDecorator<T, ID> {

    private final Keycloak keycloak;
    private final UsersResource usersResource;

    public SecureDecorator(IDataService<T, ID> delegate, Keycloak keycloak, UsersResource usersResource) {
        super(delegate);
        this.keycloak = keycloak;
        this.usersResource = usersResource;
    }

    @Override
    public T createTarget(T target) throws CreationException {
        T created = super.createTarget(target);

        if (created instanceof Contact contact) {
            try {
                UserRepresentation newUser = new UserRepresentation();
                newUser.setUsername(contact.getUsername());
                newUser.setEmail(contact.getEmail());
                newUser.setEnabled(true);

                Response response = usersResource.create(newUser);
                String keycloakId = CreatedResponseUtil.getCreatedId(response);

                contact.setKeycloakId(keycloakId);
                ((Contact) created).setKeycloakId(keycloakId);
            } catch (Exception e) {
                throw new CreationException("Error creating user in Keycloak: " + e.getMessage());
            }
        }

        return created;
    }

    @Override
    public T updateTarget(T target) throws UpdateException {
        T updated = super.updateTarget(target);

        if (updated instanceof Contact contact) {
            String keycloakId = contact.getKeycloakId();
            if (keycloakId == null) {
                throw new UpdateException("This user does not have a Keycloak ID associated.");
            }

            try {
                UserRepresentation userRep = usersResource.get(keycloakId).toRepresentation();
                userRep.setUsername(contact.getUsername());
                userRep.setEmail(contact.getEmail());
                userRep.setEnabled(true);
                usersResource.get(keycloakId).update(userRep);
            } catch (Exception e) {
                throw new UpdateException("Error updating user in Keycloak: " + e.getMessage());
            }
        }

        return updated;
    }

    @Override
    public void deleteTarget(ID idTarget) throws DeleteException {
        Optional<T> targetOpt = super.findTargetById(idTarget);
        super.deleteTarget(idTarget);

        targetOpt.ifPresent(t -> {
            if (t instanceof Contact contact && contact.getKeycloakId() != null) {
                try {
                    usersResource.get(contact.getKeycloakId()).remove();
                } catch (Exception ignored) {}
            }
        });
    }
}