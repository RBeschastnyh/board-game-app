create table if not exists ${current_schema}.sessions (
    id uuid not null primary key,
    session_id uuid not null,
    user_id uuid not null,
    valid_from timestamp with time zone not null,
    valid_to timestamp with time zone not null,
    last_login timestamp with time zone not null,

    constraint fk_user_session foreign key (user_id) references ${current_schema}.users (id)
);

comment on table ${current_schema}.sessions is 'Сессии';
comment on column ${current_schema}.sessions.id is 'Уникальный идентификатор';
comment on column ${current_schema}.sessions.session_id is 'Идентификатор сессии на уровне приложения';
comment on column ${current_schema}.sessions.user_id is 'Пользователь, которому принадлежит сессия';
comment on column ${current_schema}.sessions.valid_from is 'Дата создания сессии';
comment on column ${current_schema}.sessions.valid_to is 'Дата окончания действия сессии';
comment on column ${current_schema}.sessions.last_login is 'Время последнего запроса сессии';