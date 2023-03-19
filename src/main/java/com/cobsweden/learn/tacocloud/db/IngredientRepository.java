package com.cobsweden.learn.tacocloud.db;

import com.cobsweden.learn.tacocloud.model.Ingredient;

import java.util.List;

public interface IngredientRepository {

  List<Ingredient> findAll();

  Ingredient findOne(String id);

  Ingredient save(Ingredient ingredient);
}
