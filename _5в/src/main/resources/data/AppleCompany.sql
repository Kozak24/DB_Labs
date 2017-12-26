DROP SCHEMA IF EXISTS applecompany;
CREATE SCHEMA IF NOT EXISTS applecompany DEFAULT CHARACTER SET utf8 ;
USE applecompany ;

DROP TABLE IF EXISTS apple;
CREATE TABLE IF NOT EXISTS apple (
  apple_id BIGINT NOT NULL AUTO_INCREMENT,
  apple_name VARCHAR(45) NOT NULL,
  inventor VARCHAR(45) NOT NULL,
  country VARCHAR(50) NULL,
  year INT NULL,
  amount INT NOT NULL,
  PRIMARY KEY (apple_id)
  ) ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS city;
CREATE TABLE IF NOT EXISTS city (
  city_id BIGINT NOT NULL AUTO_INCREMENT,
  city VARCHAR(25) NOT NULL,
  PRIMARY KEY (city_id)
  ) ENGINE = InnoDB
AUTO_INCREMENT = 1 
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS warehouse;
CREATE TABLE IF NOT EXISTS warehouse (
  warehouse_id BIGINT NOT NULL AUTO_INCREMENT,
  warehousename VARCHAR(25) NOT NULL,
  logo VARCHAR(45) NOT NULL,
  email VARCHAR(45) NULL,
  city_id BIGINT NULL,
  street VARCHAR(30) NULL,
  apartment VARCHAR(10) NULL,
  PRIMARY KEY (warehouse_id),
  CONSTRAINT fk_warehouse_city1
    FOREIGN KEY (city_id)
    REFERENCES applecompany.city (city_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS warehouse_apple;
CREATE TABLE IF NOT EXISTS warehouse_apple (
  warehouse_id BIGINT NOT NULL,
  apple_id BIGINT NOT NULL,
  PRIMARY KEY (warehouse_id, apple_id),
  CONSTRAINT warehouseapple_ibfk_1
    FOREIGN KEY (warehouse_id)
    REFERENCES applecompany.warehouse (warehouse_id),
  CONSTRAINT warehouseapple_ibfk_2
    FOREIGN KEY (apple_id)
    REFERENCES applecompany.apple (apple_id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
