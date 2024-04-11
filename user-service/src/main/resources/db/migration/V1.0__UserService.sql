create table users
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
    user_role    varchar not null,
    constraint users_pk primary key (id),
    constraint users_email_key unique (email)
);

create table audit_user
(
    id bigint generated always as identity,
    created_by varchar not null,
    created_at timestamp not null,
    update_by varchar not null,
    update_at timestamp not null,
    constraint audit_user primary key (id)
);