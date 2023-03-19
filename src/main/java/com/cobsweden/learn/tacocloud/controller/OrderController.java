package com.cobsweden.learn.tacocloud.controller;

import com.cobsweden.learn.tacocloud.model.Order;
import com.cobsweden.learn.tacocloud.service.TacoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
class OrderController {

  private final TacoService tacoService;

  OrderController(TacoService tacoService) {
    this.tacoService = tacoService;
  }

  @GetMapping("/current")
  String orderForm(Model model) {
    return "orderForm";
  }

  @PostMapping
  String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
    if (errors.hasErrors()) {
      return "orderForm";
    }

    tacoService.save(order);
    sessionStatus.setComplete();
    log.info("Order submitted: {}", order);
    return "redirect:/";
  }
}
