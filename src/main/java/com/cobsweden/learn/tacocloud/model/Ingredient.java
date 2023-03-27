package com.cobsweden.learn.tacocloud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@Entity
public class Ingredient implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  private String id;

  private String name;

  @Enumerated(EnumType.STRING)
  private Type type;

  public enum Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE;

    public String text() {
      return this.name().toLowerCase();
    }
  }

}
