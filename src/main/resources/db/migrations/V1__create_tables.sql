create table ${current_schema}.users (
    id                bigint primary key generated always as identity,
    tg_id             int not null unique,
    tesera_name       varchar(200) not null,
    unique(tg_id, tesera_name)
);

create table ${current_schema}.games (
    id                    bigint primary key generated always as identity not null,
    user_id               bigint references ${current_schema}.users(id) not null,
    title                 varchar(200) not null,
    rating_my             decimal(38, 2),
    tesera_url            varchar(500) not null,
    players_min           int,
    players_max           int,
    players_min_recommend int,
    players_max_recommend int,
    playtime_min          int,
    playtime_max          int
);