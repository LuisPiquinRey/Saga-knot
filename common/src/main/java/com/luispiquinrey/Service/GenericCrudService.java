package com.luispiquinrey.Service;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.Entities.Target;
import com.luispiquinrey.Error.CreationException;
import com.luispiquinrey.Error.DeleteException;
import com.luispiquinrey.Error.SearchException;
import com.luispiquinrey.Error.UpdateException;

public class GenericCrudService<T extends Target<V>, V> implements ICrudInterface<T, V> {

    private static final Logger logger = LoggerFactory.getLogger(GenericCrudService.class);
    protected final JpaRepository<T, V> repositoryGeneric;
    private final String nameClass;

    public GenericCrudService(JpaRepository<T, V> repositoryGeneric){
        this.repositoryGeneric = repositoryGeneric;
        this.nameClass = repositoryGeneric.getClass().getSimpleName();
    }

    @Override
    public void deleteTarget(V idTarget) throws DeleteException {
        logger.info("🗑️ Deleting {} with id={}", nameClass, idTarget);
        if (idTarget == null) {
            throw new DeleteException(nameClass + " ID cannot be null");
        }
        if (!repositoryGeneric.existsById(idTarget)) {
            throw new DeleteException(nameClass + " with id " + idTarget + " not found");
        }
        try {
            repositoryGeneric.deleteById(idTarget);
            logger.info("✅ {} deleted successfully with ID: {}", nameClass, idTarget);
        } catch (Exception e) {
            logger.error("❌ Error deleting {} with ID: {}", nameClass, idTarget, e);
            throw new DeleteException("Error deleting " + nameClass + " with ID: " + idTarget);
        }
    }

    @Override
    public T createTarget(T target) throws CreationException {
        logger.info("🆕 Creating new {}", nameClass);
        if (target == null) {
            throw new CreationException(nameClass + " object cannot be null");
        }
        try {
            T savedTarget = repositoryGeneric.save(target);
            logger.info("✅ {} created successfully", nameClass);
            return savedTarget;
        } catch (Exception e) {
            logger.error("❌ Error creating " + nameClass, e);
            throw new CreationException("Error creating " + nameClass);
        }
    }

    @Override
    public T updateTarget(T target) throws UpdateException {
        logger.info("📝 Updating {}: {}", nameClass, target != null ? target.getId() : "null");
        if (target == null || target.getId() == null) {
            throw new UpdateException(nameClass + " or ID cannot be null");
        }
        if (!repositoryGeneric.existsById(target.getId())) {
            throw new UpdateException(nameClass + " not found with ID: " + target.getId());
        }
        try {
            T savedTarget = repositoryGeneric.save(target);
            logger.info("✅ {} updated successfully: {}", nameClass, savedTarget.getId());
            return savedTarget;
        } catch (Exception e) {
            logger.error("❌ Error updating {} ID: {}", nameClass, target.getId(), e);
            throw new UpdateException("Error updating " + nameClass + " with ID: " + target.getId());
        }
    }

    @Override
    public Optional<T> findTarget(V idTarget) throws SearchException {
        logger.info("🔍 Searching {} with id={}", nameClass, idTarget);
        if (idTarget == null) {
            throw new SearchException(nameClass + " ID cannot be null");
        }
        try {
            Optional<T> target = repositoryGeneric.findById(idTarget);
            if (target.isEmpty()) {
                logger.warn("{} not found with ID: {}", nameClass, idTarget);
            }
            return target;
        } catch (Exception e) {
            logger.error("❌ Error searching {} with ID: {}", nameClass, idTarget, e);
            throw new SearchException("Error searching " + nameClass + " with ID: " + idTarget);
        }
    }

    @Override
    public List<T> findAllTargets() throws SearchException {
        logger.info("📋 Listing all {}", nameClass);
        try {
            List<T> targets = repositoryGeneric.findAll();
            logger.info("✅ Retrieved {} {}", targets.size(), nameClass);
            return targets;
        } catch (Exception e) {
            logger.error("❌ Error retrieving {}", nameClass, e);
            throw new SearchException("Error retrieving " + nameClass);
        }
    }
}