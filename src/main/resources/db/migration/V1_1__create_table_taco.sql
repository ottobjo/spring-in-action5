create table if not exists taco (
    id identity,
    name varchar(50) not null,
    created_at timestamp not null
);