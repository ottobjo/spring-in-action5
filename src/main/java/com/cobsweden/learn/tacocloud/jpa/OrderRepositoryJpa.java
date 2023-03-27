package com.cobsweden.learn.tacocloud.jpa;

import com.cobsweden.learn.tacocloud.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepositoryJpa extends CrudRepository<Order, Long> {
}
