--CREATE
--    SEQUENCE hibernate_sequence;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table "user"
(
    id bigint primary key,
    "uuid" uuid default uuid_generate_v4(),
    login varchar(50) unique not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    password varchar(50) not null,
    email varchar(50) not null,
    cost decimal(5,2) not null,
    user_role varchar(50) not null,
    constraint CH_Cost_Not_Negative check (cost > 0.00),
    constraint CH_Email_Format check (email like '%_@_%_.__%'),
    constraint CH_Role_Allowed_Values check (user_role in ('ADMIN','MANAGER','EMPLOYEE'))
);


