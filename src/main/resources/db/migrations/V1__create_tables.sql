create schema if not exists ${current_schema};

create table ${current_schema}.users (
    id                bigint primary key generated always as identity,
    tg_id             int not null unique,
    tesera_name       varchar(200),
    tesera_id         integer,
    unique(tg_id, tesera_name)
);

comment on table ${current_schema}.users is 'Пользователи сервиса';
comment on column ${current_schema}.users.id is 'Системный идентификатор пользователя';
comment on column ${current_schema}.users.tg_id is 'Идентификатор пользователя в Telegram';
comment on column ${current_schema}.users.tesera_name is 'Логин пользователя на Tesera';
comment on column ${current_schema}.users.tesera_id is 'Идентификатор пользователя в Tesera';