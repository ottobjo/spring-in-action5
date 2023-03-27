create table if not exists taco_order (
    id identity,
    order_name varchar(50) not null,
    order_street varchar (50) not null,
    order_postal_code varchar (5) not null,
    order_postal_address varchar (50) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar (5) not null,
    cc_cvv varchar (3) not null,
    created_at timestamp not null
);