package com.cobsweden.learn.tacocloud.service;

import com.cobsweden.learn.tacocloud.jpa.IngredientRepositoryJpa;
import com.cobsweden.learn.tacocloud.jpa.OrderRepositoryJpa;
import com.cobsweden.learn.tacocloud.jpa.TacoRepositoryJpa;
import com.cobsweden.learn.tacocloud.model.Ingredients;
import com.cobsweden.learn.tacocloud.model.Order;
import com.cobsweden.learn.tacocloud.model.Taco;
import org.springframework.stereotype.Service;


@Service
public class TacoService {

  private final IngredientRepositoryJpa ingredientRepository;
  private final TacoRepositoryJpa tacoRepository;

  private final OrderRepositoryJpa orderRepository;

  public TacoService(IngredientRepositoryJpa ingredientRepository, TacoRepositoryJpa tacoRepository, OrderRepositoryJpa orderRepository) {
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
