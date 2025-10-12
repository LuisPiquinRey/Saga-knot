package com.luispiquinrey.Service;

import java.util.concurrent.CompletableFuture;

public interface IBaseCrudCommandService<CREATE, UPDATE, DELETE> {
    CompletableFuture<String> create(CREATE createCommand);
    CompletableFuture<Void> update(UPDATE updateCommand);
    CompletableFuture<Void> delete(DELETE deleteCommand);
}