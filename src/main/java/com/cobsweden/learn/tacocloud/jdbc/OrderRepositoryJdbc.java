package com.cobsweden.learn.tacocloud.jdbc;

import com.cobsweden.learn.tacocloud.db.OrderRepository;
import com.cobsweden.learn.tacocloud.model.Order;
import com.cobsweden.learn.tacocloud.model.Taco;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

  private final ObjectMapper objectMapper;

  @Autowired
  public OrderRepositoryJdbc(JdbcTemplate jdbc) {
    this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("taco_order").usingGeneratedKeyColumns("id");
    this.orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("taco_order_tacos");
    this.objectMapper = JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .build();
  }

  @Override
  public Order save(Order order) {
    order.setCreated_at(LocalDateTime.now());
    Long orderId = saveOrderDetails(order);
    order.setId(orderId);

    for (Taco taco : order.getTacos()) {
      saveTacoToOrder(taco, orderId);
    }
    return order;
  }

  private long saveOrderDetails(Order order) {
    Map<String, Object> values = objectMapper.convertValue(order, Map.class);
    values.put("created_at", order.getCreated_at());
    return orderInserter.executeAndReturnKey(values).longValue();
  }

  private void saveTacoToOrder(Taco taco, long orderId) {
    Map<String, Object> values = Map.of("taco_order", orderId, "taco", taco.getId());
    orderTacoInserter.execute(values);
  }

}
