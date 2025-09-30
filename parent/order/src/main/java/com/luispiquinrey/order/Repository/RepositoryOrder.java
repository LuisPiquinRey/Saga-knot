package com.luispiquinrey.order.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.order.Entities.Order;

public interface RepositoryOrder extends JpaRepository<Order, String>{
}
