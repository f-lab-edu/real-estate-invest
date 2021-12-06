drop table if exists group_item;
drop table if exists comparing_group;
drop table if exists user;
-- drop table if exists apartment_prices;
-- drop table if exists apartment;

create table if not exists user (
    user_no bigint not null auto_increment,
    id varchar(20) unique not null,
    password varchar(60) not null,
    join_dttm datetime default current_timestamp not null,
    last_login_dttm datetime default current_timestamp not null,
    delete_dttm datetime,
    primary key (`user_no`)
)DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

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
)DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

-- alter table apartment add index idx_unique_apartment (regional_code,dong,jibun,apartment_name);

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
)DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

CREATE TABLE if not exists soaring_apartment_price (
     id int(11) NOT NULL COMMENT '가격정보 ID',
     apartment_id bigint DEFAULT NULL COMMENT '아파트 ID',
     past_date date DEFAULT NULL COMMENT '비교할 과거 날짜',
     past_price bigint(20) DEFAULT NULL COMMENT '비교할 과거 금액 (만원)',
     latest_date date DEFAULT NULL COMMENT '비교할 최근 날짜',
     latest_price bigint(20) DEFAULT NULL COMMENT '비교할 최근 금액 (만원)',
     price_difference_unit varchar(10) DEFAULT NULL COMMENT '가격 비교 단위 ex) 퍼센트, 원, 달러',
     price_difference float DEFAULT NULL COMMENT '비교한 금액',
     create_ddtm datetime DEFAULT NULL COMMENT '데이터 생성 날짜',
     PRIMARY KEY (id),
     foreign key (apartment_id) references apartment(id)
);


create table if not exists comparing_group(
      id bigint not null auto_increment,
      user_no bigint not null,
      name varchar(40),
      delete_dttm datetime,
      primary key(id),
      foreign key(user_no) references user(user_no),
      index idx_user_no (user_no)
);

create table if not exists group_item(
     id bigint not null auto_increment,
     group_id bigint not null,
     apartment_id bigint not null,
     delete_dttm datetime,
     primary key(id),
     foreign key(group_id) references comparing_group(id),
     foreign key(apartment_id) references apartment(id),
     index idx_group_id_apartment_id (group_id)
);