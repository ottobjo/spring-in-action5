package com.cobsweden.learn.tacocloud.db;

import com.cobsweden.learn.tacocloud.model.Order;

public interface OrderRepository {

  Order save(Order order);

}
