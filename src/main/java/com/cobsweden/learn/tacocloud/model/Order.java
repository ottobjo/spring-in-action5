package com.cobsweden.learn.tacocloud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "taco_order")
public class Order implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime createdAt;

  @NotBlank(message = "Name is required")
  private String orderName;

  @NotBlank(message = "Street is required")
  private String orderStreet;

  @Digits(integer = 5, fraction = 0, message = "Postal code are five digits with no space")
  private String orderPostalCode;

  @NotBlank(message = "Postal city is required")
  private String orderPostalAddress;

  @CreditCardNumber(message = "Not a valid credit card number")
  private String ccNumber;

  @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
  private String ccExpiration;

  @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
  @Column(name = "cc_cvv")
  private String ccCVV;

  @ManyToMany(targetEntity = Taco.class)
  @JoinTable(name="taco_order_tacos",
      joinColumns=@JoinColumn(name="order_id", referencedColumnName="id"),
      inverseJoinColumns= @JoinColumn(name="taco_id", referencedColumnName="id")
  )
  private final List<Taco> tacos = new ArrayList<>();

  public void addTaco(Taco taco) {
    tacos.add(taco);
  }

  @PrePersist
  void createdAt() {
    this.createdAt = LocalDateTime.now();
  }

}
