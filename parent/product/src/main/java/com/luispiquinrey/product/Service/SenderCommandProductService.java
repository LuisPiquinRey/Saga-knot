package com.luispiquinrey.product.Service;

import java.util.concurrent.CompletableFuture;

import com.google.common.base.Optional;
import com.luispiquinrey.Service.SenderCommandService;
import com.luispiquinrey.Service.ISenderCommandService;
import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.Command.DeleteProductCommand;
import com.luispiquinrey.product.Command.UpdateProductCommand;
import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Repository.RepositoryProduct;

import io.axoniq.axonserver.grpc.command.Command;

public class SenderCommandProductService extends SenderCommandService implements ISenderCommandService<CreateProductCommand, UpdateProductCommand, DeleteProductCommand>{

    @Override
    public CompletableFuture<String> create(CreateProductCommand createCommand) {
        return super.sendAndWait(createCommand);
    }

    @Override
    public CompletableFuture<Void> update(UpdateProductCommand updateCommand) {
        return super.sendAndWait(updateCommand);
    }

    @Override
    public CompletableFuture<Void> delete(DeleteProductCommand deleteCommand) {
        return super.sendAndWait(deleteCommand);
    }
}
