use test;
CREATE TABLE IF NOT EXISTS cars (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     number_car VARCHAR(255) NOT NULL unique,
                                     count_place INT,
                                     date_reg DATE,
                                     status INT NOT NULL,
                                     driver_id INT NOT NULL
)  ENGINE=INNODB;

use test;
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
    status   INT          NOT NULL,
    login    VARCHAR(255) NOT NULL unique,
    password VARCHAR(255) NOT NULL
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


INSERT INTO `test`.`cars` (`id`, `number_car`, `count_place`, `date_reg`, `status`, `driver_id`) VALUES ('1', '1221AB-1', '16', STR_TO_DATE('1-01-2012', '%d-%m-%Y'), '1', '1');

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

