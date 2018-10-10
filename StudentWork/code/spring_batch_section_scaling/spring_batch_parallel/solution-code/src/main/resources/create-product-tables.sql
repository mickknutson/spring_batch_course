drop table if exists product;

create table product
(
  id character(9) not null,
  name character varying(100),  
  unit_price float,
  quantity int,
  total_amount float,
  update_timestamp timestamp,
  constraint product_pkey primary key (id)
);