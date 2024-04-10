create table feedback
(
    id        bigint generated always as identity,
    user_id   bigint not null,
    event_id  bigint not null,

    constraint feedback_pk primary key (id)
);

create table question
(
    id   bigint generated always as identity,
    name varchar not null,
    type varchar not null,

    constraint question_pk primary key (id)
);

create table event_questions
(
    id          bigint generated always as identity,
    event_id    bigint not null,
    question_id bigint not null,

    constraint event_questions_pk primary key (id)
);

create table event_questions_feedback
(
    id                bigint generated always as identity,
    event_question_id bigint not null,
    feedback_id       bigint not null,
    answer            varchar not null,

    constraint event_questions_feedback_pk primary key (id)
);
