package com.cobsweden.learn.tacocloud.service;

import com.cobsweden.learn.tacocloud.db.IngredientRepository;
import com.cobsweden.learn.tacocloud.model.Ingredients;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

  private final IngredientRepository ingredientRepository;

  public IngredientService(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  public Ingredients ingredients() {
    return new Ingredients(ingredientRepository.findAll());
  }

}
