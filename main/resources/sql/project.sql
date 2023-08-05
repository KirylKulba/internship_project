CREATE TABLE IF NOT EXISTS project
(
    id bigint NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4(),
    name varchar(50) NOT NULL,
    description varchar(255) ,
    begin_date date NOT NULL,
    end_date date NOT NULL,
    budget decimal(10,2) NOT NULL,
    budget_use decimal(5,2) NOT NULL,
    CONSTRAINT project_pkey PRIMARY KEY (id),
    CONSTRAINT project_name_key UNIQUE (name),
    CONSTRAINT ch_budget_not_negative CHECK (budget > 0.00),
    CONSTRAINT ch_begin_is_before_end check (begin_date < end_date)
);