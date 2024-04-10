create table categories
(
    id         bigint generated always as identity,
    name       varchar   not null,
    created_at timestamp not null,
    updated_at timestamp not null,

    constraint categories_pk primary key (id),
    constraint categories_name_key unique (name)
);

create table event_categories
(
    id          bigint generated always as identity,
    event_id    bigint not null,
    category_id bigint not null,

    constraint event_categories_pk primary key (id),
    constraint event_categories_category_id_fk foreign key (category_id) references categories
);

create table user_categories
(
    id          bigint generated always as identity,
    user_id     bigint not null,
    category_id bigint not null,

    constraint user_categories_pk primary key (id),
    constraint user_categories_category_id_fk foreign key (category_id) references categories
);
