package com.cobsweden.learn.tacocloud.jdbc;

import com.cobsweden.learn.tacocloud.db.IngredientRepository;
import com.cobsweden.learn.tacocloud.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class IngredientRepositoryJdbc implements IngredientRepository {

  private final JdbcTemplate jdbc;

  @Autowired
  public IngredientRepositoryJdbc(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public List<Ingredient> findAll() {
    return jdbc.query("select id, name, type from ingredient", this::rowMapper);
  }

  @Override
  public Ingredient findOne(String id) {
    return jdbc.queryForObject("Select id, name type from ingredient where id = ?", this::rowMapper, id);
  }

  @Override
  public Ingredient save(Ingredient ingredient) {
    jdbc.update("insert into ingredient (id, name, type) values (?, ?, ?)",
        ingredient.getId(),
        ingredient.getName(),
        ingredient.getType().toString());
    return ingredient;
  }

  private Ingredient rowMapper(ResultSet rs, int rowNum) throws SQLException {
    return Ingredient.builder()
        .id(rs.getString("id"))
        .name(rs.getString("name"))
        .type(Ingredient.Type.valueOf(rs.getString("type")))
        .build();

  }

}
