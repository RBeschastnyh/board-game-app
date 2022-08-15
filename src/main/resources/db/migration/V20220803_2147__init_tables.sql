create table guest (
  id serial primary key,
  login varchar(40) not null,
  full_name varchar(150) not null,
  password varchar(150) not null
);

comment on table guest is 'Information about your beloved guest';
comment on column guest.id is 'Guest''s unique identifier';
comment on column guest.login is 'Guest''s login';
comment on column guest.full_name is 'Guest''s full name';
comment on column guest.password is 'Guest''s password';

create table cocktail (
    id serial primary key,
    cocktail_name varchar(100) not null,
    description varchar(200) not null,
    allergens varchar(200),
    ingredients varchar(200) not null,
    tags varchar(200) not null,
    is_stirred boolean
);

comment on table cocktail is 'Table for all cocktails';
comment on column cocktail.id is 'Cocktail unique identifier';
comment on column cocktail.cocktail_name is 'Cocktail name in english';
comment on column cocktail.description is 'Remarks about drink by author';
comment on column cocktail.allergens is 'Allergens in the drink, divided by a comma';
comment on column cocktail.ingredients is 'Ingredients in the drink with names and volumes, divided by a comma';
comment on column cocktail.tags is 'Tags for quicker search, divided by a comma';
comment on column cocktail.is_stirred is 'Shows whether this drink available after 11pm';

create table feedback (
  id serial primary key,
  author_id serial,
  cocktail_id serial,
  feedback_text varchar(500) not null,
  rating int not null,
  constraint fk_author_feedback foreign key(author_id) references guest(id),
  constraint fk_cocktail_feedback foreign key(cocktail_id) references cocktail(id)
);

comment on table feedback is 'Users feedback about cocktails';
comment on column feedback.id is 'Feedback unique identifier';
comment on column feedback.author_id is 'Unique identifier of the author - links guest';
comment on column feedback.cocktail_id is 'Feedback object - links cocktail';
comment on column feedback.feedback_text is 'Content of the feedback';
comment on column feedback.rating is 'Drink rate by user';
