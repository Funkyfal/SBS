CREATE TABLE person
(
    id           SERIAL PRIMARY KEY,
    second_name  VARCHAR(10) NOT NULL,
    name         VARCHAR(10) NOT NULL,
    middle_name  VARCHAR(10) NOT NULL,
    city         VARCHAR(10) NOT NULL,
    address      VARCHAR(15) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    post_code    INT         NOT NULL
);

create type STATUS_ENUM AS ENUM ('IS_NOT_READY', 'IN_PROGRESS', 'COMPLETE', 'CANCELED');

CREATE TABLE myOrder
(
    number    INT UNIQUE NOT NULL,
    delivery  BOOLEAN    NOT NULL,
    date      DATE       NOT NULL,
    status    STATUS_ENUM,
    person_id INT REFERENCES person (id)
);

CREATE TABLE good
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(15) CHECK (name != '') NOT NULL,
    short_name   VARCHAR(10) CHECK (short_name != '' AND LENGTH(name) > LENGTH(short_name)) NOT NULL,
    description VARCHAR(100) CHECK (description != '')                                   NOT NULL,
    quantity    INT CHECK (quantity > 0)                                                 NOT NULL,
    price       DECIMAL(10, 2) CHECK (price > 0)                                         NOT NULL,
    order_number INT REFERENCES myOrder (number)
);

INSERT INTO person(second_name, name, middle_name, city, address, phone_number, post_code)
values ('Popova', 'Darya', 'Sergeevna', 'Slonim', 'Alibegova St.', '+37533-------', 220020);

INSERT INTO person(second_name, name, middle_name, city, address, phone_number, post_code)
values ('Bosko', 'Anton', 'Pavlovich', 'Minsk', 'K. Marksa St.', '+37544-------', 220020);

insert into myOrder(number, Delivery, date, status, person_id)
values (1, true, '2025-2-6', 'IS_NOT_READY', 2);
insert into myOrder(number, Delivery, date, status, person_id)
values (2, false, '2035-2-6', 'CANCELED', 1);

insert into good(name, short_name, description, quantity, price, order_number)
values ('Pasta', 'Ps', 'Italian pasta for the whole family', 20, 49.99, 1);
insert into good(name, short_name, description, quantity, price, order_number)
values ('Pepperoni', 'Peppa', 'Delicious pepperoni for yummy pizza', 15, 32.53, 1);
insert into good(name, short_name, description, quantity, price, order_number)
values ('Pickles', 'Rick', 'The best choice of Rick Sanchez', 52, 9.85, 1);
insert into good(name, short_name, description, quantity, price, order_number)
values ('Feelin', 'Fe', 'The best choice of Anton Dosko', 138, 0.92, 2);
insert into good(name, short_name, description, quantity, price, order_number)
values ('Cake-pops', 'Jum-Jum', 'Even better than Mill-pops', 31, 1.48, 2);

alter table good
drop
column quantity;
alter table myOrder
    add column sum_of_an_order DECIMAL;

create table order_goods
(
    good_id      int references good (id),
    order_number int references myOrder (number),
    quantity     int,
    primary key (good_id, order_number)
);

CREATE OR REPLACE FUNCTION calculate_order_sum()
RETURNS TRIGGER AS $$
BEGIN
UPDATE myOrder
SET sum_of_an_order = (
    SELECT SUM(good.price * order_goods.quantity)
    FROM order_goods
             JOIN good ON good.id = order_goods.good_id
    WHERE order_goods.order_number = NEW.order_number
    GROUP BY order_goods.order_number
)
WHERE number = NEW.order_number;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_order_sum
    AFTER INSERT OR UPDATE OR DELETE ON order_goods
    FOR EACH ROW
    EXECUTE FUNCTION calculate_order_sum();


insert into order_goods(good_id, order_number, quantity)
values (1, 1, 10);
insert into order_goods(good_id, order_number, quantity)
values (2, 1, 20);
insert into order_goods(good_id, order_number, quantity)
values (3, 1, 30);
insert into order_goods(good_id, order_number, quantity)
values (4, 2, 40);
insert into order_goods(good_id, order_number, quantity)
values (5, 2, 50);
