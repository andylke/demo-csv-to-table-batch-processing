
DROP TABLE IF EXISTS public.customer_details;

CREATE TABLE public.customer_details (
  id UUID not null,
  customer_number decimal(20, 0) not null,
  type_code varchar(50) not null,
  name varchar(300) not null,
  CONSTRAINT customer_details_pk PRIMARY KEY (id),
  CONSTRAINT customer_details_uk UNIQUE(customer_number)
);


DROP TABLE IF EXISTS public.product_details;

CREATE TABLE public.product_details (
  id UUID not null,
  type_code varchar(50) not null,
  currency_code varchar(3) not null,
  description varchar(300) null,
  CONSTRAINT product_details_pk PRIMARY KEY (id),
  CONSTRAINT product_details_uk UNIQUE(type_code)
);


DROP TABLE IF EXISTS public.account_details;

CREATE TABLE public.account_details (
  id UUID not null,
  account_number decimal(20, 0) not null,
  customer_number decimal(20, 0) not null,
  product_type_code varchar(50) not null,
  currency_code varchar(3) not null,
  balance decimal(25, 4) null,
  CONSTRAINT account_details_pk PRIMARY KEY (id),
  CONSTRAINT account_details_uk UNIQUE(account_number)
);
