package com.cobsweden.learn.tacocloud.db;

import com.cobsweden.learn.tacocloud.model.Taco;

public interface TacoRepository {

  Taco save(Taco taco);

}
