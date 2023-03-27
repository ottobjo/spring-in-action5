package com.cobsweden.learn.tacocloud.jpa;

import com.cobsweden.learn.tacocloud.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepositoryJpa extends JpaRepository<Ingredient, String> {

}
