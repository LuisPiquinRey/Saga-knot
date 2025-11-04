package com.luispiquinrey.order.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.order.Entities.Order;

public interface RepositoryOrder extends JpaRepository<Order, String> {

    Optional<Order> findByIdUser(String idUser);

    List<Order> findAllByIdUser(String idUser);
}
