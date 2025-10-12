package com.luispiquinrey.Service;

import java.util.Optional;

import com.luispiquinrey.Error.SearchException;

public class BaseQueryService<T,ID> implements IBaseQueryService<T, ID> {

    @Override
    public Optional<T> findTargetById(ID idTarget) throws SearchException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findTargetById'");
    }
    
}
