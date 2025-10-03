package com.luispiquinrey.order.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luispiquinrey.order.Entities.Item;

public interface RepositoryItem extends MongoRepository<Item, String>{
    
}
