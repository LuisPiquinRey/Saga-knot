package com.luispiquinrey.cart.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luispiquinrey.cart.Entities.Item;

public interface RepositoryItem extends MongoRepository<Item, String>{
    
}
