create table "user_project"
(
    user_id bigint,
    project_id bigint,
    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES "user" (id) ,
    CONSTRAINT project_fk FOREIGN KEY (project_id) REFERENCES "project" (id) ,
    primary key (user_id, project_id)
);
