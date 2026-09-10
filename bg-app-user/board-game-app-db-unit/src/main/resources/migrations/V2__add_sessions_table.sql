create table if not exists ${current_schema}.sessions (
    id uuid not null primary key,
    user_id uuid not null,
    valid_from timestamp with time zone not null,
    valid_to timestamp with time zone not null,
    last_login timestamp with time zone not null,

    constraint fk_user_session foreign key (user_id) references ${current_schema}.users (id)
);

comment on table ${current_schema}.users is 'Польователи';
comment on column ${current_schema}.users.id is 'Уникальный идентификатор';
comment on column ${current_schema}.users.login is 'Сгенерированный логин пользователя';
comment on column ${current_schema}.users.username is 'Имя пользователя (задаётся пользователем)';
comment on column ${current_schema}.users.email is 'Электронная почта, на которую отправлялось приглашение';
comment on column ${current_schema}.users.password is 'Хеш пароля';