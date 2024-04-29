create table if not exists users
(
    id           bigint generated always as identity,
    first_name   varchar not null,
    last_name    varchar not null,
    email        varchar not null,
    password     varchar not null,
    faculty      varchar not null,
    group_name   varchar not null,
    phone_number varchar not null,
    avatar_id    bigint,
    role         varchar not null,
    created_at timestamp not null,
    updated_at timestamp not null,

    constraint users_pk primary key (id),
    constraint users_email_key unique (email)
);

create table if not exists audit_user
(
    id         bigint generated always as identity,
    created_by varchar   not null,
    created_at timestamp not null,
    updated_by varchar   not null,
    updated_at timestamp not null,

    constraint audit_user_pk primary key (id)
);

create table if not exists user_categories
(
    id          bigint generated always as identity,
    user_id     bigint not null,
    category_id bigint not null,

    constraint user_categories_pk primary key (id),
    constraint user_categories_user_id_fk foreign key (user_id) references users
);