create table cocktail_group
(
    id                bigint primary key not null,
    group_name        varchar(50),
    group_description varchar(255)
);

comment
on table cocktail_group is 'Группы коктейлей для разделения по эпохам';
comment
on column cocktail_group.id is 'Идентификатор группы';
comment
on column cocktail_group.group_name is 'Название группы';
comment
on column cocktail_group.group_description is 'Описание группы';

-----------------------------------

create type supply as enum ('NONE', 'FEW', 'LOT');

comment
on type supply is 'Мера наличия коктейля в баре. Значения: нет, немного, много. Влияет на отображение на ЭФ';

create table cocktail
(
    id           serial primary key,
    name         varchar(100) not null,
    ingredients  varchar(200) not null,
    description  varchar(500),
    is_stirred   boolean,
    group_id     bigint       not null,
    availability supply not null,
    constraint fk_cocktail_group foreign key (group_id) references cocktail_group (id)
);

comment
on table cocktail is 'Таблица с коктейлями';
comment
on column cocktail.id is 'Идентификатор напитка в системе';
comment
on column cocktail.name is 'Название коктейля (английское)';
comment
on column cocktail.ingredients is 'Ингридиенты, входящие в состав коктейля';
comment
on column cocktail.description is 'Описание напитка';
comment
on column cocktail.is_stirred is 'Смешивается коктейль или взбалтывается';
comment
on column cocktail.group_id is 'Идентификатор группы коктейля';
comment
on column cocktail.availability is 'Мера наличия коктейля в баре';

-----------------------------------

create table guest
(
    id        serial primary key,
    login     varchar(40)  not null,
    full_name varchar(150) not null,
    password  varchar(150) not null
);

comment
on table guest is 'Информация о госте';
comment
on column guest.id is 'Идентификатор гостя в системе';
comment
on column guest.login is 'Логин гостя';
comment
on column guest.full_name is 'Имя гостя';
comment
on column guest.password is 'Пароль гостя';

-----------------------------------

create table tags
(
    id              serial primary key not null,
    tag_name        varchar(100),
    tag_description varchar(255)
);

comment
on table tags is 'Тэги для фильтрации коктейлей';
comment
on column tags.id is 'Идентификатор тэга';
comment
on column tags.tag_name is 'Название тэга для поиска';
comment
on column tags.tag_description is 'Описание тэга для вывода на ЭФ';

-----------------------------------

create table tagmap
(
    id          bigint primary key not null,
    cocktail_id bigint             not null,
    tag_id      bigint             not null,
    constraint fk_cocktail_id_tag_map foreign key (cocktail_id) references cocktail (id),
    constraint fk_tag_id_tag_map foreign key (tag_id) references tags (id)
);

comment
on table tagmap is 'Таблица сопоставления коктейля и тэгов';
