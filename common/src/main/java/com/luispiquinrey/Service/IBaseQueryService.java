package com.luispiquinrey.Service;

import java.util.Optional;

import com.luispiquinrey.Error.SearchException;

public interface IBaseQueryService<T,ID> {
    Optional<T> findTargetById(ID idTarget) throws SearchException;
}
