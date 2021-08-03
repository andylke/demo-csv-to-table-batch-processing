CREATE TABLE public.customer_details (
  id UUID not null,
  number decimal(20, 0) not null,
  type varchar(50) not null,
  name varchar(300) not null,
  CONSTRAINT customer_details_pk PRIMARY KEY (id),
  CONSTRAINT customer_details_uk UNIQUE(number)
);
