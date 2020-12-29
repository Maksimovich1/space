use test;
CREATE TABLE IF NOT EXISTS cars (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     number_car VARCHAR(255) NOT NULL unique,
                                     count_place INT,
                                     date_reg DATE,
                                     status INT NOT NULL,
                                     driver_id INT NOT NULL
)  ENGINE=INNODB;

INSERT INTO `test`.`cars` (`id`, `number_car`, `count_place`, `date_reg`, `status`, `driver_id`) VALUES ('1', '1221AB-1', '16', STR_TO_DATE('1-01-2012', '%d-%m-%Y'), '1', '1');