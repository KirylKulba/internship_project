insert into "user"
    (id,login,first_name,last_name,password,email,cost,user_role)
values
    (nextval('hibernate_sequence'), 'user1', 'Kiryl',
        'Kulbachynski','user','bulbik@mail.com',20.50,'ADMIN');

insert into "user"
(id,login,first_name,last_name,password,email,cost,user_role)
values
(nextval('hibernate_sequence'), 'user2', 'Bulbik',
 'Kulbachynski2','user','bulbik@mail.com',20.50,'EMPLOYEE');