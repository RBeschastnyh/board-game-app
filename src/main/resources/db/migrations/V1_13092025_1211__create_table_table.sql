create table if not exists ${current_schema}.tabletop (
    id         bigint primary key generated always as identity not null,
    code       varchar(6) not null,
    host_id    bigint references ${current_schema}.users(id) not null,
    created_at timestamp not null,
    expires_at timestamp not null,
    is_shut    boolean default false,
    unique(code)
);

comment on table ${current_schema}.tabletop is 'Столы, созданные пользователями';
comment on column ${current_schema}.tabletop.id is 'Идентификатор';
comment on column ${current_schema}.tabletop.code is 'Сгенерированный код';
comment on column ${current_schema}.tabletop.host_id is 'Идентификатор пользователя, создавшего стол';
comment on column ${current_schema}.tabletop.created_at is 'Дата создания';
comment on column ${current_schema}.tabletop.expires_at is 'Дата истечения';
comment on column ${current_schema}.tabletop.is_shut is 'Флаг закрытия';