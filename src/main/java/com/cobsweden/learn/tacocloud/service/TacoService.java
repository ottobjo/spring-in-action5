package com.cobsweden.learn.tacocloud.service;

import com.cobsweden.learn.tacocloud.db.IngredientRepository;
import com.cobsweden.learn.tacocloud.db.OrderRepository;
import com.cobsweden.learn.tacocloud.db.TacoRepository;
import com.cobsweden.learn.tacocloud.model.Ingredients;
import com.cobsweden.learn.tacocloud.model.Order;
import com.cobsweden.learn.tacocloud.model.Taco;
import org.springframework.stereotype.Service;

@Service
public class TacoService {

  private final IngredientRepository ingredientRepository;
  private final TacoRepository tacoRepository;

  private final OrderRepository orderRepository;

  public TacoService(IngredientRepository ingredientRepository, TacoRepository tacoRepository, OrderRepository orderRepository) {
    this.ingredientRepository = ingredientRepository;
    this.tacoRepository = tacoRepository;
    this.orderRepository = orderRepository;
  }

  public Ingredients ingredients() {
    return new Ingredients(ingredientRepository.findAll());
  }

  public Taco save(Taco taco) {
    return tacoRepository.save(taco);
  }

  public Order save(Order order) {
    return orderRepository.save(order);
  }

}
