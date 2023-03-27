package com.cobsweden.learn.tacocloud.jdbc;

import com.cobsweden.learn.tacocloud.model.Order;
import com.cobsweden.learn.tacocloud.model.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@Repository
public class OrderRepositoryJdbc implements OrderRepository {

  private final SimpleJdbcInsert orderInserter;

  private final SimpleJdbcInsert orderTacoInserter;


  @Autowired
  public OrderRepositoryJdbc(JdbcTemplate jdbc) {
    this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("taco_order").usingGeneratedKeyColumns("id");
    this.orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("taco_order_tacos");
  }

  @Override
  public Order save(Order order) {
    order.setCreatedAt(LocalDateTime.now());
    long orderId = saveOrderDetails(order);
    order.setId(orderId);

    for (Taco taco : order.getTacos()) {
      saveTacoToOrder(taco, orderId);
    }
    return order;
  }

  private long saveOrderDetails(Order order) {
    Map<String, Object> values = Map.of(
        "order_name", order.getOrderName(),
        "order_street", order.getOrderStreet(),
        "order_postal_code", order.getOrderPostalCode(),
        "order_postal_address", order.getOrderPostalAddress(),
        "cc_number", order.getCcNumber(),
        "cc_expiration", order.getCcExpiration(),
        "cc_Cvv", order.getCcCVV(),
        "created_at", order.getCreatedAt()
    );
    return orderInserter.executeAndReturnKey(values).longValue();
  }

  private void saveTacoToOrder(Taco taco, long orderId) {
    Map<String, Object> values = Map.of("taco_order", orderId, "taco", taco.getId());
    orderTacoInserter.execute(values);
  }

}
