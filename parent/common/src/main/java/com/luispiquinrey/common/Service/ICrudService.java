package com.luispiquinrey.common.Service;

import java.util.Optional;

import com.luispiquinrey.common.Entities.BaseEntity;
import com.luispiquinrey.common.Error.CreationException;
import com.luispiquinrey.common.Error.DeleteException;
import com.luispiquinrey.common.Error.SearchException;
import com.luispiquinrey.common.Error.UpdateException;

public interface ICrudService<T extends BaseEntity<ID>,ID> {
    Optional<T> findTargetById(ID idTarget) throws SearchException;
    void deleteTarget(ID idTarget) throws DeleteException;
    T createTarget(T target) throws CreationException;
    T updateTarget(T target) throws UpdateException;
}
