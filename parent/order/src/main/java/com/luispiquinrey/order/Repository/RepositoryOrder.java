package com.luispiquinrey.order.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.luispiquinrey.order.Entities.Order;

public interface RepositoryOrder extends MongoRepository<Order, String>{
}
