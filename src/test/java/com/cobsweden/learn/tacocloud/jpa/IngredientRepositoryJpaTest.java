package com.cobsweden.learn.tacocloud.jpa;

import com.cobsweden.learn.tacocloud.model.Ingredient;
import com.cobsweden.learn.tacocloud.model.Ingredient.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IngredientRepositoryJpaTest {
  private static final Ingredient NOT_FOUND = Ingredient.builder().id(null).name("not found").build();

  @Autowired
  private IngredientRepositoryJpa repository;

  @Test
  void saveDoesNotThrow() {
    Ingredient expected = ingredientBuilder().build();
    assertDoesNotThrow(() -> repository.saveAndFlush(expected));
  }

  @Test
  void saveWhenIdNullThenThrow() {
    Ingredient expected = ingredientBuilder().id(null).build();
    assertThrows(JpaSystemException.class, () -> repository.saveAndFlush(expected));
  }

  @Test
  void saveWhenNameNullThenThrow() {
    Ingredient invalid = ingredientBuilder().name(null).build();
    assertThrows(DataIntegrityViolationException.class, () ->repository.saveAndFlush(invalid));
  }

  @Test
  void saveWhenTypeNullThenThrow() {
    Ingredient invalid = ingredientBuilder().type(null).build();
    assertThrows(DataIntegrityViolationException.class, () ->repository.saveAndFlush(invalid));
  }

  @Test
  void readReturnsSavedEntry() {
    Ingredient expected = ingredientBuilder().build();
    repository.save(expected);

    Ingredient actual = repository.findById(expected.getId()).orElse(NOT_FOUND);
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getType(), actual.getType());
  }

  private Ingredient.IngredientBuilder ingredientBuilder() {
    return Ingredient.builder().id("ID").name("name").type(Type.WRAP);
  }

}