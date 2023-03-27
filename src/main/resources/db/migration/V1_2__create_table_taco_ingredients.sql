create table if not exists taco_ingredients (
    taco_id bigint not null,
    ingredient_id varchar(4) not null
);

alter table taco_ingredients add foreign key (taco_id) references taco (id);
alter table taco_ingredients add foreign key (ingredient_id) references ingredient (id);