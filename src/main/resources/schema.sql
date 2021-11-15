drop table if exists user;
# drop table if exists apartment_prices;
# drop table if exists apartment;

create table if not exists user (
    user_no bigint not null auto_increment,
    id varchar(20) unique not null,
    password varchar(60) not null,
    join_dttm datetime default current_timestamp not null,
    last_login_dttm datetime default current_timestamp not null,
    delete_dttm datetime,
    primary key (`user_no`)
);

create table if not exists apartment (
    id bigint not null auto_increment,
    regional_code char(5) not null,
    city varchar(10),
    gu varchar(10),
    dong varchar(10),
    jibun varchar(10),
    bonbun char(4),
    bubun char(4),
    apartment_name varchar(50),
    build_year smallint,
    road_address varchar(50),
    delete_dttm datetime,
    primary key (`id`),
    index idx_unique_apartment (regional_code,dong,jibun,apartment_name)
);

# alter table apartment add index idx_unique_apartment (regional_code,dong,jibun,apartment_name);

create table if not exists apartment_prices (
     id bigint not null auto_increment,
     apartment_id bigint not null,
     area_for_exclusive_use float,
     deal_year smallint,
     deal_month tinyint,
     deal_day tinyint,
     deal_amount int,
     floor smallint,
     delete_dttm datetime,
     primary key (`id`),
     foreign key (apartment_id) references apartment(id)
);