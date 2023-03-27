package com.cobsweden.learn.tacocloud.jpa;

import com.cobsweden.learn.tacocloud.model.Ingredient;
import com.cobsweden.learn.tacocloud.model.Taco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TacoRepositoryJpaTest {

  private Ingredient ingredient1;

  private Ingredient ingredient2;

  @Autowired
  private TacoRepositoryJpa tacoRepository;

  @Autowired
  private IngredientRepositoryJpa ingredientRepository;

  @BeforeEach
  void setup() {
    ensureIngredients();
  }

  @Test
  void ensureSaveDoesNotThrow() {
    Taco expected = Taco.builder().name("taco name").ingredients(List.of(ingredient1, ingredient2)).build();
    assertDoesNotThrow(() -> tacoRepository.saveAndFlush(expected));
  }

  @Test
  void ensureReadReturnsPopulatedEntity() {
    Taco expected = Taco.builder().name("taco name").ingredients(List.of(ingredient1, ingredient2)).build();

    Long id = tacoRepository.saveAndFlush(expected).getId();

    Taco actual = tacoRepository.getReferenceById(expected.getId());
    assertEquals(id, actual.getId());
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getIngredients(), actual.getIngredients());
    assertNotNull(actual.getCreatedAt());
  }

  private void ensureIngredients() {
    if (ingredient1 == null) {
      ingredient1 = ingredientRepository.getReferenceById("FLTO");
    }
    if (ingredient2 == null) {
      ingredient2 = ingredientRepository.getReferenceById("COTO");
    }
  }
}