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
    uut.setCcExpiration("11/22");

    Set<ConstraintViolation<Order>> violations = validator.validate(uut);
    assertTrue(violations.isEmpty(), "errors + " + violations);

  }

  Order newOrder() {
    return Order.builder()
        .orderName("a")
        .orderStreet("b")
        .orderPostalCode("1")
        .orderPostalAddress("city")
        .ccExpiration("123")
        .ccExpiration("01/22")
        .ccCVV("123")
        .build();
  }
}