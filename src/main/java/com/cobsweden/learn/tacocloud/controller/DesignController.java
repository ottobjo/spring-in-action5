package com.cobsweden.learn.tacocloud.controller;

import com.cobsweden.learn.tacocloud.aop.LogAop;
import com.cobsweden.learn.tacocloud.model.Ingredients;
import com.cobsweden.learn.tacocloud.model.Taco;
import com.cobsweden.learn.tacocloud.service.IngredientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping("/design")
class DesignController {

  private final IngredientService ingredientService;

  @Autowired
  DesignController(IngredientService ingredientService) {
    this.ingredientService = ingredientService;
  }

  @ModelAttribute(name = "taco")
  Taco taco() {
    return new Taco();
  }

  @GetMapping
  @LogAop(operation = "showDesigns")
  String showDesigns(Model model) {
    addIngredients(model);
    return "design";
  }

  @PostMapping
  @LogAop(operation = "processDesign")
  String processDesign(Model model, @Valid Taco taco, Errors errors) {
    if (errors.hasErrors()) {
      addIngredients(model);
      return "design";
    }
    return "redirect:/orders/current";
  }

  private void addIngredients(Model model) {
    Ingredients ingredients = ingredientService.ingredients();
    ingredients.types().forEach(type -> model.addAttribute(type.text(), ingredients.ofType(type)));
  }
}
