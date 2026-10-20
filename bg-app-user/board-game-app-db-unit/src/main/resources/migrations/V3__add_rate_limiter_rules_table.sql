create type ${current_schema}.timeunit as enum ('SEC', 'MIN', 'HOUR', 'DAY', 'YEAR', 'ETR');

create table if not exists ${current_schema}.rate_limit_rules (
    id uuid primary key not null,
    path varchar(200) unique not null,
    limits smallint not null,
    units ${current_schema}.timeunit not null
);

comment on table ${current_schema}.rate_limit_rules is 'Ограничения запросов пользователей';
comment on column ${current_schema}.rate_limit_rules.id is 'Идентификатор';
comment on column ${current_schema}.rate_limit_rules.path is 'Путь к которому происходит ограничение трафика';
comment on column ${current_schema}.rate_limit_rules.limits is 'Количественное ограничение';
comment on column ${current_schema}.rate_limit_rules.units is 'Единицы измерения ограничения';