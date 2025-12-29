package com.luispiquinrey.common.Utilities;

import com.luispiquinrey.common.Entities.BaseEntity;
import com.luispiquinrey.common.Error.CreationException;
import com.luispiquinrey.common.Error.DeleteException;
import com.luispiquinrey.common.Error.SearchException;
import com.luispiquinrey.common.Error.UpdateException;

import java.util.Optional;

public abstract class BaseDecorator<T extends BaseEntity<ID>, ID>
        implements IDataService<T, ID> {

    protected final IDataService<T, ID> delegate;

    protected BaseDecorator(IDataService<T, ID> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Optional<T> findTargetById(ID idTarget) throws SearchException {
        return delegate.findTargetById(idTarget);
    }

    @Override
    public void deleteTarget(ID idTarget) throws DeleteException {
        delegate.deleteTarget(idTarget);
    }

    @Override
    public T createTarget(T target) throws CreationException {
        return delegate.createTarget(target);
    }

    @Override
    public T updateTarget(T target) throws UpdateException {
        return delegate.updateTarget(target);
    }
}

