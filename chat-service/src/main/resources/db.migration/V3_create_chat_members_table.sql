create table chat_members(
    user_id bigint,
    chat_id bigint,
    foreign key (user_id) references users(id),
    foreign key (chat_id) references chat(id)
);

