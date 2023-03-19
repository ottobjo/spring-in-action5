package com.cobsweden.learn.tacocloud.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

  private Long id;

  private LocalDateTime created_at;

  @NotBlank(message = "Name is required")
  private String order_name;

  @NotBlank(message = "Street is required")
  private String order_street;

  @Digits(integer = 5, fraction = 0, message = "Postal code are five digits with no space")
  private String order_postal_code;

  @NotBlank(message = "Postal city is required")
  private String order_postal_address;

  @CreditCardNumber(message = "Not a valid credit card number")
  private String cc_number;

  @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
  private String cc_expiration;

  @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
  private String cc_CVV;

  private final List<Taco> tacos = new ArrayList<>();

  public void addTaco(Taco taco) {
    tacos.add(taco);
  }
}
