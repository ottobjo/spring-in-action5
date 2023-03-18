package com.cobsweden.learn.tacocloud.service;

import com.cobsweden.learn.tacocloud.model.Ingredient;
import com.cobsweden.learn.tacocloud.model.Ingredient.Type;
import com.cobsweden.learn.tacocloud.model.Ingredients;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
  public Ingredients ingredients() {
    return new Ingredients(List.of(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
        new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
        new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
        new Ingredient("CARN", "Carnitas", Type.PROTEIN),
        new Ingredient("TMTO", "Diced Tomateos", Type.VEGGIES),
        new Ingredient("LETC", "Lettuce", Type.VEGGIES),
        new Ingredient("CHED", "Cheddar", Type.CHEESE),
        new Ingredient("JACK", "Monterrrey Jack", Type.CHEESE),
        new Ingredient("SLSA", "Salsa", Type.SAUSE),
        new Ingredient("SRCR", "Sour Cream", Type.SAUSE))
    );
  }
}
