package com.cobsweden.learn.tacocloud.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

  private ValidatorFactory validatorFactory;
  private Validator validator;

  @BeforeEach
  void setup() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @AfterEach
  void teardown() {
    validatorFactory.close();
  }

  @Test
  void getCcCVV() {
    Order uut = newOrder();
    uut.setCc_expiration("11/22");

    Set<ConstraintViolation<Order>> violations = validator.validate(uut);
    assertTrue(violations.isEmpty(), "errors + " + violations);

  }

  Order newOrder() {
    return Order.builder()
        .order_name("a")
        .order_street("b")
        .order_postal_code("1")
        .order_postal_address("city")
        .cc_expiration("123")
        .cc_expiration("01/22")
        .cc_CVV("123")
        .build();
  }
}