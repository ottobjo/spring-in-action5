package com.cobsweden.learn.tacocloud.controller;

import com.cobsweden.learn.tacocloud.model.Ingredient;
import com.cobsweden.learn.tacocloud.model.Ingredient.Type;
import com.cobsweden.learn.tacocloud.model.Ingredients;
import com.cobsweden.learn.tacocloud.model.Taco;
import com.cobsweden.learn.tacocloud.service.TacoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DesignController.class)
class DesignControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TacoService tacoService;

  @BeforeEach
  void setup() {
    when(tacoService.ingredients()).thenReturn(new Ingredients(
        List.of(
            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        )));
  }

  @Test
  void testShowDesignForm() throws Exception {
    mockMvc.perform(get("/design"))
        .andExpect(status().isOk())
        .andExpect(view().name("design"))
        .andExpect(model().attribute("cheese", List.of(
            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE)
        )));
  }

  @Test
  void processDesign() throws Exception {
    var flto = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
    var grbf = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
    var ched = new Ingredient("CHED", "Cheddar", Type.CHEESE);

    var taco = new Taco();
    taco.setName("Taco Test");
    taco.setIngredients(List.of(flto,grbf,ched));

    when(tacoService.save(taco)).thenReturn(taco);

    mockMvc.perform(post("/design")
        .flashAttr("taco", taco))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().stringValues("Location", "/orders/current"));
  }

}
