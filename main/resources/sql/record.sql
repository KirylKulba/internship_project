create table "record"
(
    id bigint NOT NULL primary key,
    uuid uuid DEFAULT uuid_generate_v4(),
    project_id bigint,
    user_id bigint,
    begin_timestamp timestamp not null,
    end_timestamp timestamp not null,
    work_cost decimal(10,2) not null,
    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE SET NULL,
    CONSTRAINT project_fk FOREIGN KEY (project_id) REFERENCES "project" (id) ON DELETE SET NULL,
    CONSTRAINT ch_begin_is_before_end check (begin_timestamp < end_timestamp),
    CONSTRAINT CH_Work_Cost_Not_Negative check (work_cost > 0.00)
);

CREATE INDEX if not exists project_index ON "record" (project_id);
CREATE INDEX if not exists user_index ON "record" (user_id);