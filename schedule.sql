create table todo (
    todo_id bigint not null auto_increment primary key,
    name varchar(50) not null,
    todo varchar(255),
    password varchar(50),
    create_at timestamp default current_timestamp,
    update_at timestamp default current_timestamp on update current_timestamp
);