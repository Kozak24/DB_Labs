USE JDBC;
CREATE SCHEMA IF NOT EXISTS `JDBC` DEFAULT CHARACTER SET utf8;
USE `JDBC`;

DROP TABLE IF EXISTS Price;
CREATE TABLE Price(
	ID_Price INT NOT NULL AUTO_INCREMENT,                       
    Price VARCHAR(25) NOT NULL,
    PRIMARY KEY (ID_Price)
) ENGINE InnoDB;

DROP TABLE IF EXISTS Warehouse;
 CREATE TABLE Warehouse (
	ID_Warehouse INT NOT NULL AUTO_INCREMENT,
    WarehouseName VARCHAR(25) NOT NULL,
    City VARCHAR(25) NOT NULL,
    Street VARCHAR(25) NOT NULL,
    Email VARCHAR(25) NOT NULL,
    PRIMARY KEY (ID_Warehouse)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS Apple;
 CREATE TABLE Apple (
	ID_Apple INT NOT NULL AUTO_INCREMENT,
    AppleName VARCHAR(25) NOT NULL,
    Amount INT NOT NULL,
    ID_Price INT NOT NULL,
    PRIMARY KEY (ID_Apple),
    CONSTRAINT FOREIGN KEY (ID_Price)
		REFERENCES Price(ID_Price)
 ) ENGINE = InnoDB;
 
DROP TABLE IF EXISTS AppleWarehouse;
CREATE TABLE AppleWarehouse (
	ID_Warehouse INT NOT NULL,
	ID_Apple INT NOT NULL,
	CONSTRAINT FOREIGN KEY (ID_Warehouse)
		REFERENCES Warehouse(ID_Warehouse),
    PRIMARY KEY (ID_Apple, ID_Warehouse),
	CONSTRAINT FOREIGN KEY (ID_Apple)
		REFERENCES Apple(ID_Apple)

) ENGINE = InnoDB;

INSERT INTO  Price VALUES
(1, '15'),
(2, '20'),
(3, '35'),
(4, '50'),
(5, '100');

INSERT INTO Apple VALUES
(1, 'David', 5, 4),
(2, 'Strauss', 35, 2), 
(3, 'Griby', 20, 3),
(4, 'Fare', 50, 1),
(5, 'Crasby', 2, 5);

INSERT INTO Warehouse VALUES
(1, 'Yabluka', 'Lviv', 'Sadowa 3', 'Yabluka@ukr.net'),
(2, 'Yabluka', 'Lviv', 'Zelena 53a', 'Yabluka@ukr.net');

INSERT INTO AppleWarehouse VALUES
(1, 1),
(2, 1),
(1, 2),
(1, 3),
(2, 3),
(2, 4),
(1, 5),
(2, 5);


DELIMITER //
CREATE PROCEDURE InsertAppleWarehouse
(
IN WarehouseNameIn varchar(25),
IN AppleNameIN varchar(45)
)
BEGIN
	DECLARE msg varchar(40);
    
  -- checks for present WarehouseName
  IF NOT EXISTS( SELECT * FROM Warehouse WHERE WarehouseName = WarehouseNameIn)
  THEN SET msg = 'This WarehouseName is absent';
    
  -- checks for present Apple
	ELSEIF NOT EXISTS( SELECT * FROM Apple WHERE AppleName = AppleNameIN)
		THEN SET msg = 'This Apple is absent';
    
  -- checks if there are this combination already
	ELSEIF EXISTS( SELECT * FROM  AppleWarehouse
		WHERE ID_Warehouse = (SELECT ID_Warehouse FROM Warehouse WHERE WarehouseName = WarehouseNameIn)
        AND ID_Apple = (SELECT ID_Apple FROM Apple WHERE AppleName = AppleNameIN)
        )
        THEN SET msg = 'This Warehouse already has this Apple';
	
  -- checks whether there is still such a Apple
	ELSEIF (SELECT Amount FROM Apple WHERE AppleName = AppleNameIN ) 
    <= (SELECT COUNT(*) FROM AppleWarehouse WHERE ID_Apple = (SELECT ID_Apple FROM Apple WHERE AppleName = AppleNameIN) )
    THEN SET msg = 'There are no this Apple already';
    
    -- makes insert
    ELSE 
		INSERT AppleWarehouse (ID_Warehouse, ID_Apple) 
        Value ( (SELECT ID_Warehouse FROM Warehouse WHERE WarehouseName = WarehouseNameIn),
			     (SELECT ID_Apple FROM Apple WHERE AppleName = AppleNameIN) );
		SET msg = 'OK';		 

	END IF;

	SELECT msg AS msg;

END //
DELIMITER ;