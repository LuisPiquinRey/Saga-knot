package com.luispiquinrey.Service;

import java.util.List;
import java.util.Optional;

import com.luispiquinrey.Error.CreationException;
import com.luispiquinrey.Error.DeleteException;
import com.luispiquinrey.Error.SearchException;
import com.luispiquinrey.Error.UpdateException;

public interface ICrudInterface<T,V> {
	void deleteTarget(V idTarget) throws DeleteException;
    T createTarget(T target) throws CreationException;;
    T updateTarget(T target) throws UpdateException;
    Optional<T> findTarget(V idTarget) throws SearchException;
    List<T> findAllTargets() throws UpdateException;

    default int sizeTargets() throws UnsupportedOperationException{
        return findAllTargets().size();
    }
}
