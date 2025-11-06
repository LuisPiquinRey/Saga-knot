package com.luispiquinrey.order.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.luispiquinrey.order.Entities.OrderItem;

public interface RepositoryOrderItem extends JpaRepository<OrderItem, String> {

    @Query("SELECT oi FROM OrderItem oi WHERE oi.idOrderItem IN (SELECT o.idOrder FROM Order o WHERE o.idOrder = :idOrder)")
    List<OrderItem> findByIdOrder(String idOrder);

    Optional<OrderItem> findByIdOrderItemAndIdProduct(String idOrderItem, String idProduct);

    @Modifying
    @Query("DELETE FROM OrderItem oi WHERE oi.idOrderItem IN (SELECT o.idOrder FROM Order o WHERE o.idOrder = :idOrder)")
    void deleteByIdOrder(String idOrder);

    @Modifying
    @Query("DELETE FROM OrderItem oi WHERE oi.idOrderItem IN (SELECT o.idOrder FROM Order o WHERE o.idOrder = :idOrder) AND oi.idProduct = :idProduct")
    void deleteByIdOrderAndIdProduct(String idOrder, String idProduct);
}
