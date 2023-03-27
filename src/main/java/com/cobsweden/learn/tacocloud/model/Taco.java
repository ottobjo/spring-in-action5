package com.cobsweden.learn.tacocloud.model;

import com.cobsweden.learn.tacocloud.aop.ModelObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

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
public class Taco implements ModelObject, Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime createdAt;

  @NotNull
  @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
  private String name;

  @NotNull
  @Size(min = 2, message = "You must choose at least two ingredients")
  @ManyToMany(targetEntity = Ingredient.class)
  @JoinTable(name="taco_ingredients",
      joinColumns=@JoinColumn(name="taco_id", referencedColumnName="id"),
      inverseJoinColumns= @JoinColumn(name="ingredient_id", referencedColumnName="id")
  )
  private List<Ingredient> ingredients = new ArrayList<>();

  public boolean has(Ingredient ingredient) {
    return ingredients != null && ingredients.stream().map(Ingredient::getId).toList().contains(ingredient.getId());
  }

  @PrePersist
  void createdAt() {
    this.createdAt = LocalDateTime.now();
  }

}
