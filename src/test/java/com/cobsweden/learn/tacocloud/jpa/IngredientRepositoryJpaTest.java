package com.cobsweden.learn.tacocloud.jpa;

import com.cobsweden.learn.tacocloud.model.Ingredient;
import com.cobsweden.learn.tacocloud.model.Ingredient.Type;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@TestPropertySource(properties = {
    "spring.flyway.enabled=true",
    "spring.jpa.hibernate.ddl-auto=none"
})
class IngredientRepositoryJpaTest {
  private static final Ingredient NOT_FOUND = Ingredient.builder().id(null).name("not found").build();

  @Autowired
  private IngredientRepositoryJpa repository;

  @Test
  void saveDoesNotThrow() {
    Ingredient expected = Ingredient.builder().id("ID").name("name").type(Type.WRAP).build();
    assertDoesNotThrow(() -> repository.saveAndFlush(expected));
  }

  @Test
  void saveToDbWhenIdNullThenThrow() {
    Ingredient expected = Ingredient.builder().id(null).name("name").type(Type.WRAP).build();
    assertThrows(JpaSystemException.class, () -> repository.saveAndFlush(expected));
  }

  @Test
  void saveToDbWhenNameNullThenThrow() {
    Ingredient invalid = Ingredient.builder().id("ID").name(null).type(Type.WRAP).build();
    assertThrows(DataIntegrityViolationException.class, () ->repository.saveAndFlush(invalid));
  }

  @Test
  void saveToDbWhenTypeNullThenThrow() {
    Ingredient invalid = Ingredient.builder().id("ID").name("name").type(null).build();
    assertThrows(DataIntegrityViolationException.class, () ->repository.saveAndFlush(invalid));
  }

  @Test
  void findByIdReturnsSavedEntry() {
    Ingredient expected = Ingredient.builder().id("ID").name("name").type(Type.WRAP).build();
    repository.save(expected);

    Ingredient actual = repository.findById(expected.getId()).orElse(NOT_FOUND);
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getType(), actual.getType());
  }

}