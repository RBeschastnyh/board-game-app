create table ${current_schema}.games (
    id                    bigint primary key generated always as identity not null,
    user_id               bigint references ${current_schema}.users(id) not null,
    tesera_id             integer,
    title                 varchar(200) not null,
    alias                 varchar(200) not null,
    rating_my             decimal(38, 2),
    tesera_url            varchar(500) not null,
    players_min           integer,
    players_max           integer,
    players_min_recommend integer,
    players_max_recommend integer,
    playtime_min          integer,
    playtime_max          integer,
    is_addition           boolean
);

comment on table ${current_schema}.games is 'Игры пользователей';
comment on column ${current_schema}.games.id is 'Системный идентификатор игры';
comment on column ${current_schema}.games.user_id is 'Идентификатор пользователя. Внешний ключ';
comment on column ${current_schema}.games.tesera_id is 'Идентификатор игры на Tesera';
comment on column ${current_schema}.games.title is 'Название игры';
comment on column ${current_schema}.games.alias is 'Строковое универсальное название';
comment on column ${current_schema}.games.rating_my is 'Пользовательский рейтинг на Tesera';
comment on column ${current_schema}.games.tesera_url is 'Ссылка на игру';
comment on column ${current_schema}.games.players_min is 'Минимальное количество игроков';
comment on column ${current_schema}.games.players_max is 'Максимальное количество игроков';
comment on column ${current_schema}.games.players_min_recommend is 'Минимальное количество игроков. Рекомендуемое';
comment on column ${current_schema}.games.players_max_recommend is 'Максимальное количество игроков. Рекомендуемое';
comment on column ${current_schema}.games.playtime_min is 'Минимальная длина партии';
comment on column ${current_schema}.games.playtime_max is 'Максимальная длина партии';
comment on column ${current_schema}.games.is_addition is 'Является ли игра дополнением';