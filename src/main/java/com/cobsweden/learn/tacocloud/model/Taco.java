package com.cobsweden.learn.tacocloud.model;

import com.cobsweden.learn.tacocloud.aop.ModelObject;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Taco implements ModelObject {

  private Long id;

  private LocalDateTime createdAt;

  @NotNull
  @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
  private String name;

  @NotNull
  @Size(min = 2, message = "You must choose at least two ingredients")
  private List<String> ingredientIds = new ArrayList<>();

  public boolean has(Ingredient ingredient) {
    return ingredientIds.contains(ingredient.getId());
  }

}
