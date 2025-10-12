package com.luispiquinrey.product.Service;

import java.util.concurrent.CompletableFuture;

import com.google.common.base.Optional;
import com.luispiquinrey.Service.BaseCommandService;
import com.luispiquinrey.Service.IBaseCrudCommandService;
import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.Command.DeleteProductCommand;
import com.luispiquinrey.product.Command.UpdateProductCommand;
import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Repository.RepositoryProduct;

import io.axoniq.axonserver.grpc.command.Command;

public class ImplServiceProduct extends BaseCommandService implements IBaseCrudCommandService<CreateProductCommand, UpdateProductCommand, DeleteProductCommand>{

    @Override
    public CompletableFuture<String> create(CreateProductCommand createCommand) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public CompletableFuture<Void> update(UpdateProductCommand updateCommand) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public CompletableFuture<Void> delete(DeleteProductCommand deleteCommand) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
