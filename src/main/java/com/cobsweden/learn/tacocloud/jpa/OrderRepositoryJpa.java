package com.cobsweden.learn.tacocloud.jpa;

import com.cobsweden.learn.tacocloud.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositoryJpa extends JpaRepository<Order, Long> {
}
