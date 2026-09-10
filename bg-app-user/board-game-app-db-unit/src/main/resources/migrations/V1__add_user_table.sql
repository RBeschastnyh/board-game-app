create table if not exists ${current_schema}.users (
    id uuid not null primary key,
    login varchar(40) not null,
    username varchar(40),
    email varchar(90) not null,
    password varchar(255)
);

comment on table ${current_schema}.users is 'Пользователи';
comment on column ${current_schema}.users.id is 'Уникальный идентификатор';
comment on column ${current_schema}.users.login is 'Сгенерированный логин пользователя';
comment on column ${current_schema}.users.username is 'Имя пользователя (задаётся пользователем)';
comment on column ${current_schema}.users.email is 'Электронная почта, на которую отправлялось приглашение';
comment on column ${current_schema}.users.password is 'Хеш пароля';