package com.cobsweden.learn.tacocloud.jdbc;

import com.cobsweden.learn.tacocloud.db.TacoRepository;
import com.cobsweden.learn.tacocloud.model.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Repository
public class TacoRepositoryJdbc implements TacoRepository {

  private final JdbcTemplate jdbc;

  @Autowired
  public TacoRepositoryJdbc(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public Taco save(Taco taco) {
    Long tacoId = saveTaco(taco);
    taco.setId(tacoId);

    for(String ingredientId : taco.getIngredientIds()) {
      saveIngredientToTaco(ingredientId, tacoId);
    }
    return taco;
  }

  private Long saveTaco(Taco taco) {
    taco.setCreatedAt(LocalDateTime.now());
    PreparedStatementCreatorFactory pscf =
        new PreparedStatementCreatorFactory("insert into taco (name, created_at) values (?, ?)", Types.VARCHAR, Types.TIMESTAMP);
    pscf.setReturnGeneratedKeys(true);
    PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(taco.getName(), taco.getCreatedAt()));
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbc.update(psc, keyHolder);
    return getId(keyHolder);
  }
  private Long getId(KeyHolder keyHolder) {
    return Optional.ofNullable(keyHolder).map(KeyHolder::getKey).map(Number::longValue).orElse(null);
  }

  private void saveIngredientToTaco(String ingredientId, long tacoId) {
    jdbc.update("insert into taco_ingredients (taco, ingredient) values (?, ?)", tacoId, ingredientId);
  }

}
