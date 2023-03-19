package com.cobsweden.learn.tacocloud.model;

import lombok.Value;

import java.util.List;
import java.util.stream.Stream;

@Value
public class Ingredients {
  List<Ingredient> ingredientList;

  public List<Ingredient> ofType(Ingredient.Type type) {
    return ingredientList.stream()
        .filter(e -> e.getType().equals(type))
        .toList();
  }

  public Stream<Ingredient.Type> types() {
    return ingredientList.stream()
        .map(Ingredient::getType)
        .distinct();
  }

}
