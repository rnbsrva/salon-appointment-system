CREATE TABLE messages
(
    id                SERIAL PRIMARY KEY,
    user_id           BIGINT,
    chat_id           BIGINT,
    date              TIMESTAMPTZ,
    message           TEXT,
    parent_message_id BIGINT,
    foreign key (user_id) references users (id),
    foreign key (chat_id) references chat (id)
);
