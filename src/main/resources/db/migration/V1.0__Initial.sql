create table users
(
    id           bigint generated always as identity,
    first_name   varchar   not null,
    last_name    varchar   not null,
    email        varchar   not null,
    password     varchar   not null,
    faculty      varchar   not null,
    group_name   varchar   not null,
    phone_number varchar   not null,
    avatar_id    bigint,
    created_at   timestamp not null,
    updated_at   timestamp not null,

    constraint users_pk primary key (id),
    constraint users_email_key unique (email)
);

create table events
(
    id          bigint generated always as identity,
    name        varchar   not null,
    description varchar   not null,
    datetime    timestamp not null,
    location    varchar   not null,
    photo_id    bigint,
    created_at  timestamp not null,
    updated_at  timestamp not null,

    constraint events_pk primary key (id)
);

create table user_events
(
    id       bigint generated always as identity,
    user_id  bigint not null,
    event_id bigint not null,

    constraint user_events_pk primary key (id),
    constraint user_events_event_id_fk foreign key (event_id) references events (id) on update cascade on delete cascade,
    constraint user_events_user_id_fk foreign key (user_id) references users (id) on update cascade on delete cascade
);

create table categories
(
    id   bigint generated always as identity,
    name varchar not null,

    constraint categories_pk primary key (id),
    constraint categories_name_key unique (name)
);

create table user_categories
(
    id          bigint generated always as identity,
    user_id     bigint not null,
    category_id bigint not null,

    constraint user_categories_pk primary key (id),
    constraint user_categories_category_id_fk foreign key (category_id) references categories (id) on update cascade on delete cascade,
    constraint user_categories_user_id_fk foreign key (user_id) references users (id) on update cascade on delete cascade
);

create table feedback
(
    id             bigint generated always as identity,
    user_id        bigint  not null,
    event_id       bigint  not null,
    visited        boolean not null,
    receive_photos boolean not null,
    comment        varchar not null,

    constraint feedback_pk primary key (id),
    constraint feedback_event_id_fk foreign key (event_id) references events (id),
    constraint feedback_user_id_fk foreign key (user_id) references users (id)
);

create table files
(
    id            bigint generated always as identity,
    name          varchar   not null,
    owner_id      bigint    not null,
    created_at    timestamp not null,
    type          varchar   not null,
    target_type   varchar   not null,
    target_id     bigint    not null,
    status        varchar   not null,
    deletion_date date,

    constraint files_pk primary key (id),
    constraint files_name_key unique (name),
    constraint files_owner_id_fk foreign key (owner_id) references users (id)
);

alter table users
    add constraint users_avatar_id_fk foreign key (avatar_id) references files (id) on update cascade on delete set null;

alter table events
    add constraint events_photo_id_fk foreign key (photo_id) references files (id) on update cascade on delete set null;
