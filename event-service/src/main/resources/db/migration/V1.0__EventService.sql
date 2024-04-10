create table events
(
    id               bigint generated always as identity,
    name             varchar                  not null,
    description      text                     not null,
    date             timestamp with time zone not null,
    location         varchar                  not null,
    photo_id         bigint,
    is_paid          boolean                  not null default false,
    price            numeric(10, 2)           not null default 0.0,
    max_participants int,
    created_at       timestamp                not null,
    updated_at       timestamp                not null,

    constraint events_pk primary key (id)
);

create table user_events
(
    id         bigint generated always as identity,
    user_id    bigint    not null,
    event_id   bigint    not null,
    status     varchar   not null,
    created_at timestamp not null,
    updated_at timestamp not null,

    constraint user_events_pk primary key (id),
    constraint user_events_events_id_fk foreign key (event_id) references events
);