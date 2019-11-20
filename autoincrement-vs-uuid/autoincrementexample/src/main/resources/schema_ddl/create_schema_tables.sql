create table customers
(
    id integer identity primary key,
    name varchar(50)
);

create table phone_numbers
(
    id integer identity primary key,
    customer_id integer,
    number varchar(50),
    foreign key (customer_id) references customers(id)
);

