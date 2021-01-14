use test;

CREATE TABLE IF NOT EXISTS analytic_type
(
    id_type     INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS analytic_value
(
    id_value    INT AUTO_INCREMENT PRIMARY KEY,
    type_id     INT          NOT NULL,
    description VARCHAR(255) NOT NULL,
    relation    int          NULL,
    foreign key (type_id) references analytic_type (id_type)
) ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS cars
(
    id                       INT AUTO_INCREMENT PRIMARY KEY,
    analytic_value_id_brand  int          not null,
    analytic_value_id_model  int          not null,
    analytic_value_id_status int          not null,
    number_car               VARCHAR(255) NOT NULL unique,
    count_place              INT,
    date_reg                 DATE,
    driver_id                INT          NOT NULL,
    owner_id    INT NOT NULL ,
    foreign key (analytic_value_id_brand) references analytic_value (id_value),
    foreign key (analytic_value_id_model) references analytic_value (id_value),
    foreign key (analytic_value_id_status) references analytic_value (id_value),
    foreign key (driver_id) references users (id),
    foreign key (owner_id) references users (id)
) ENGINE = INNODB;


# таблица клиентов пока клиеты использоваться не будут
# CREATE TABLE IF NOT EXISTS clients
# (
#     id                INT AUTO_INCREMENT PRIMARY KEY,
#     name_organization VARCHAR(255) NOT NULL unique,
#     stars             INT,
#     date_reg          DATE,
#     status            INT          NOT NULL,
#     type              INT          NOT NULL
# ) ENGINE = INNODB;
#
CREATE TABLE IF NOT EXISTS users
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    date_reg DATE,
    analytic_value_id_status   INT          NOT NULL,
    login    VARCHAR(255) NOT NULL unique,
    password VARCHAR(255) NOT NULL,
    analytic_value_id_type int not null
) ENGINE = INNODB;


# параметры для клиента
# CREATE TABLE IF NOT EXISTS client_params
# (
#     id         INT AUTO_INCREMENT PRIMARY KEY,
#     clientId   INT,
#     nameParam  VARCHAR(255) NOT NULL,
#     valueParam VARCHAR(255) NOT NULL,
#     foreign key (clientId) references clients (id)
# ) ENGINE = INNODB;

# параметры для юзера
CREATE TABLE IF NOT EXISTS user_params
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    userId     INT,
    nameParam  VARCHAR(255) NOT NULL,
    valueParam VARCHAR(255) NOT NULL,
    foreign key (userId) references users (id)
) ENGINE = INNODB;



CREATE TABLE IF NOT EXISTS external_codes
(
    externalCode INT PRIMARY KEY ,
    messageRU    VARCHAR(255) NOT NULL
) ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS internal_codes
(
    internalCode INT PRIMARY KEY,
    message    VARCHAR(255) NOT NULL,
    externalId INT NOT NULL,
    foreign key (externalId) references external_codes (externalCode)
) ENGINE = INNODB;


insert into external_codes values (-9999, 'Ошибка на сервере');
insert into external_codes values (-5555, 'Ошибка с соединением');
insert into internal_codes values (-10111, 'Ошибка с соединением', -5555);

insert into analytic_type (description)
values ('Тип документа');
insert into analytic_type (description)
values ('Статус доступа автомобиля');
insert into analytic_type (description)
values ('Бренд машины');
insert into analytic_type (description)
values ('Модель машины');
insert into analytic_type (description)
values ('Тип аккаунта');
insert into analytic_type (description)
values ('Тип статуса аккаунта');


insert into analytic_value (type_id, description)
values (1, 'Недоступна (на ремонте)');
insert into analytic_value (type_id, description)
values (1, 'Недоступна (отпуск)');

insert into analytic_value (type_id, description)
values (2, 'Mercedes');
insert into analytic_value (type_id, description)
values (2, 'Volkswagen');
insert into analytic_value (type_id, description)
values (2, 'Volvo');
insert into analytic_value (type_id, description)
values (2, 'Opel');

insert into analytic_value (type_id, description)values (4, 'Перевозчик');
insert into analytic_value (type_id, description)values (4, 'Водитель');
insert into analytic_value (type_id, description)values (4, 'Заказчик');

insert into analytic_value (type_id, description, relation)values (3, 'Sprinter', 5);
insert into analytic_value (type_id, description, relation)values (3, 'Crafter', 6);
insert into analytic_value (type_id, description, relation)values (3, 'Zafira', 8);


insert into users (date_reg, analytic_value_id_status, login, password, analytic_value_id_type)values (STR_TO_DATE('1-01-2012', '%d-%m-%Y'), 0, 'adrew', '1111', 13);
insert into users (date_reg, analytic_value_id_status, login, password, analytic_value_id_type)values (STR_TO_DATE('1-08-2018', '%d-%m-%Y'), 0, 'jonh', '1111', 12);



insert into cars (analytic_value_id_brand, analytic_value_id_model, analytic_value_id_status, number_car, count_place, date_reg, driver_id, owner_id)
values (5, 9, 2, 'AB1112-1', 16, sysdate() , 3, 1);



