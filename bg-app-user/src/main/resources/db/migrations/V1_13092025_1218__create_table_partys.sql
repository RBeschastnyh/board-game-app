create table if not exists ${current_schema}.partys (
    user_id               bigint references ${current_schema}.users(id) not null,
    table_id              bigint references ${current_schema}.tabletop(id) not null,
    state                 boolean default false,

    constraint pk_parties_table primary key (user_id, table_id)
);

comment on table ${current_schema}.partys is 'Список всех пользователей и столов, к которым они присоединялись';
comment on column ${current_schema}.partys.user_id is 'Системный идентификатор пользователя';
comment on column ${current_schema}.partys.table_id is 'Идентификатор стола';
comment on column ${current_schema}.partys.state is 'Статус стола. Открыт - true';