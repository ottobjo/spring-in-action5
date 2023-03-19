package com.cobsweden.learn.tacocloud.controller;

import com.cobsweden.learn.tacocloud.aop.LogAop;
import com.cobsweden.learn.tacocloud.model.Ingredients;
import com.cobsweden.learn.tacocloud.model.Order;
import com.cobsweden.learn.tacocloud.model.Taco;
import com.cobsweden.learn.tacocloud.service.TacoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
class DesignController {

  private final TacoService tacoService;

  @Autowired
  DesignController(TacoService tacoService) {
    this.tacoService = tacoService;
  }

  @ModelAttribute(name = "order")
  Order order(Model model) {
    return new Order();
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
  String processDesign(Model model, @Valid Taco taco, Errors errors, Order order) {
    if (errors.hasErrors()) {
      addIngredients(model);
      return "design";
    }
    Taco savedTaco = tacoService.save(taco);
    order.addTaco(savedTaco);
    return "redirect:/orders/current";
  }

  private void addIngredients(Model model) {
    Ingredients ingredients = tacoService.ingredients();
    ingredients.types().forEach(type -> model.addAttribute(type.text(), ingredients.ofType(type)));
  }
}
