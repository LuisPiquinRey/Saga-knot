package com.luispiquinrey.Service;

import java.util.Optional;

import com.luispiquinrey.Entities.BaseEntity;
import com.luispiquinrey.Error.CreationException;
import com.luispiquinrey.Error.DeleteException;
import com.luispiquinrey.Error.SearchException;
import com.luispiquinrey.Error.UpdateException;

public interface ICrudService<T extends BaseEntity<ID>,ID> {
    Optional<T> findTargetById(ID idTarget) throws SearchException;
    void deleteTarget(ID idTarget) throws DeleteException;
    T createTarget(T target) throws CreationException;
    T updateTarget(T target) throws UpdateException;
}
