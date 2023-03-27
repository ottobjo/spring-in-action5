package com.cobsweden.learn.tacocloud.jpa;

import com.cobsweden.learn.tacocloud.model.Ingredient;
import com.cobsweden.learn.tacocloud.model.Taco;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.jpa.properties.javax.persistence.validation.mode=none",
})
class TacoRepositoryJpaTest {

  private final Ingredient ingredient1;

  private final Ingredient ingredient2;

  private final TacoRepositoryJpa tacoRepository;

  @Autowired
  TacoRepositoryJpaTest(IngredientRepositoryJpa ingredientRepository, TacoRepositoryJpa tacoRepository) {
    this.tacoRepository = tacoRepository;
    this.ingredient1 = ingredientRepository.getReferenceById("FLTO");
    this.ingredient2 = ingredientRepository.getReferenceById("COTO");
  }

  @Test
  void saveDoesNotThrow() {
    Taco expected = tacoBuilder().build();
    assertDoesNotThrow(() -> tacoRepository.saveAndFlush(expected));
  }

  @Test
  void saveWhenNameNullThenThrow() {
    Taco expected = tacoBuilder().name(null).build();
    var ex = assertThrows(DataIntegrityViolationException.class, () -> tacoRepository.saveAndFlush(expected));
    System.out.println(ex.getMessage());
  }

  @Test
  void readReturnsSavedEntry() {
    Taco expected = tacoBuilder().build();

    Long id = tacoRepository.saveAndFlush(expected).getId();

    Taco actual = tacoRepository.getReferenceById(expected.getId());
    assertEquals(id, actual.getId());
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getIngredients(), actual.getIngredients());
    assertNotNull(actual.getCreatedAt());
  }

  private Taco.TacoBuilder tacoBuilder() {
    return Taco.builder().name("taco name").ingredients(List.of(ingredient1, ingredient2));
  }

}