 create table balance (
    id bigint not null,
    currency_type varchar(255) not null,
    deposit decimal(19,2) not null,
    user_id bigint not null,
    version bigint not null,
    primary key (id)
);

create table transaction (
    id bigint not null,
    amount decimal(19,2) not null,
    counted decimal(19,2) not null,
    date timestamp not null,
    deleted boolean not null,
    transaction_currency_type varchar(255) not null,
    transaction_type varchar(255) not null,
    balance_id bigint not null,
    primary key (id)
);

 create table user (
    id bigint not null,
    email varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    password varchar(255) not null,
    role varchar(255) not null,
    primary key (id)
);

create sequence hibernate_sequence start with 1 increment by 1;

alter table balance
    add constraint balance_user_fk
    foreign key (user_id) references user;

alter table transaction
    add constraint transaction_balance_fk
    foreign key (balance_id) references balance;
