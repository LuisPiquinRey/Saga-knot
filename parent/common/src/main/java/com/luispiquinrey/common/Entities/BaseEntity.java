package com.luispiquinrey.common.Entities;

public abstract class BaseEntity<ID> {
    public abstract ID getId();
    public abstract void setId(ID id);
}
