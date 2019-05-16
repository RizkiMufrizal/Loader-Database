DROP TABLE IF EXISTS tb_person;

CREATE TABLE tb_person  (
    person_id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name_person VARCHAR(50),
    address_person VARCHAR(50),
    birthday_person VARCHAR(50)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 AUTO_INCREMENT=1;