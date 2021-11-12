drop table if exists user;

create table if not exists user (
      user_no bigint not null auto_increment,
      id varchar(20) unique not null,
      password varchar(60) not null,
      join_dttm datetime default current_timestamp not null,
      last_login_dttm datetime default current_timestamp not null,
      delete_dttm datetime,
      primary key (`user_no`)
);