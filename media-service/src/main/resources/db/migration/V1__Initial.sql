create table if not exists file
(
    id            bigint       not null generated always as identity,
    name          varchar(255) not null,
    owner_id      bigint       not null,
    created_at    timestamp    not null,
    type          varchar      not null,
    target_type   varchar      not null,
    target_id     bigint       not null,
    status        varchar      not null,
    deletion_date date,

    constraint file_pk primary key (id),
    constraint file_name_uq unique (name)
);

create table if not exists file_audit
(
    id               bigserial not null,
    file_id          bigint    not null,
    user_id          bigint,
    user_ip          varchar   not null,
    interaction_type varchar   not null,
    interaction_time timestamp not null,

    constraint file_audit_reference_file_fk foreign key (file_id) references file (id) on delete cascade on update cascade
);