create table if not exists taco_order_tacos (
    order_id bigint not null,
    taco_id bigint not null
);

alter table taco_order_tacos add foreign key (order_id) references taco_order(id);
alter table taco_order_tacos add foreign key (taco_id) references taco(id);