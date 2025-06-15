package com.example.myfirstapp.zip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myfirstapp.zip.entities.OrderItem;
import com.example.myfirstapp.zip.entities.OrderItemPK;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}