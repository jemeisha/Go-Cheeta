DROP DATABASE go_cheeta;
CREATE DATABASE IF NOT EXISTS go_cheeta;
USE go_cheeta;

CREATE TABLE `customer`
(
    `username`   varchar(45) NOT NULL,
    `password`   varchar(45) NOT NULL,
    `first_name` varchar(45) NOT NULL,
    `last_name`  varchar(45) DEFAULT NULL,
    `mobile_no`  varchar(10) NOT NULL,
    PRIMARY KEY (`username`)
);
CREATE TABLE `branch`
(
    `branch_id`   int         NOT NULL,
    `branch_name` varchar(45) NOT NULL,
    `phoneNo`     varchar(10) NOT NULL,
    PRIMARY KEY (`branch_id`)
);

CREATE TABLE `category`
(
    `category_id`   int         NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    PRIMARY KEY (`category_id`)


);
CREATE TABLE `vehicle`
(
    `vehicle_no`   varchar(45) NOT NULL,
--     `driver_id`    int         NOT NULL,
    `vehicle_category` int NOT NULL,
    `noOfSeats`    int DEFAULT NULL,
    `colour`       varchar(45) NOT NULL,
    PRIMARY KEY (`vehicle_no`),
    --  KEY            `vehi_driver_idx` (`driver_id`),
--     CONSTRAINT `vehi_driver` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `vehicle_category` FOREIGN KEY (`vehicle_category`) REFERENCES `category` (`category_id`)
);
CREATE TABLE `driver`
(
    `driver_id`  int         NOT NULL AUTO_INCREMENT,
    `password`   varchar(45) NOT NULL,
    `first_name` varchar(45) NOT NULL,
    `last_name`  varchar(45) DEFAULT NULL,
    `NIC`        varchar(12) NOT NULL,
    `mobile`     varchar(10) NOT NULL,
    `branch_id`  int         NOT NULL,
    `vehicle_no`   varchar(45) NOT NULL,

    PRIMARY KEY (`driver_id`),
    KEY          `branch_id_idx` (`branch_id`),
    CONSTRAINT `branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`),
    CONSTRAINT `vehicle_no` FOREIGN KEY (`vehicle_no`) REFERENCES `vehicle` (`vehicle_no`)
);

CREATE TABLE `order`
(
    `order_id`      int         NOT NULL AUTO_INCREMENT,
    `username`   varchar(45) NOT NULL,
    `vehicle_no`    varchar(45) NOT NULL,
    `driver_id`    int NOT NULL,
    `pickup`        int NOT NULL,
    `destination`   int NOT NULL,
    `total`         double        NOT NULL,
    `booking_state` int NOT NULL,
    -- 0 DRIVER_ARRIVING
    -- 1 DRIVER_ARRIVED
    -- 2 TRIP_STARTED
    -- 3 TRIP_ENDED
    PRIMARY KEY (`order_id`)  ,
    CONSTRAINT `order_cus` FOREIGN KEY (`username`) REFERENCES `customer` (`username`),
    CONSTRAINT `branch_pickup` FOREIGN KEY (`pickup`) REFERENCES `branch` (`branch_id`),
    CONSTRAINT `branch_destination` FOREIGN KEY (`destination`) REFERENCES `branch` (`branch_id`),
    CONSTRAINT `order_driver` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`));


CREATE TABLE `distance`
(
    `distance_one` int NOT NULL,
    `distance_two` int NOT NULL,
    `distance` double NOT NULL,

    PRIMARY KEY (`distance_one`,`distance_two`),
    CONSTRAINT `distanceonebranch` FOREIGN KEY (`distance_one`) REFERENCES `branch` (`branch_id`),
    CONSTRAINT `distancetwobranch` FOREIGN KEY (`distance_two`) REFERENCES `branch` (`branch_id`));


-- ------------Insert statement------------------------

INSERT INTO `branch` (`branch_id`,`branch_name`,`phoneNo`)
VALUES (1,'Nugegoda','0112454599');
INSERT INTO `branch` (`branch_id`, `branch_name`, `phoneNo`)
VALUES (2, 'Gampaha', '0112929888');
INSERT INTO `branch` (`branch_id`, `branch_name`, `phoneNo`)
VALUES (3, 'Galle', '0112556677');
INSERT INTO `branch` (`branch_id`, `branch_name`, `phoneNo`)
VALUES (4, 'Kandy', '0112121121');
INSERT INTO `branch` (`branch_id`, `branch_name`, `phoneNo`)
VALUES (5, 'Kurunegala', '0112331212');
INSERT INTO `branch` (`branch_id`, `branch_name`, `phoneNo`)
VALUES (6, 'Jaffna', '0112775461');


INSERT INTO `customer` (`username`, `password`, `first_name`, `last_name`, `mobile_no`)
VALUES ('king', 'king123', 'Kingsley', 'Almeida', '0722893708');
INSERT INTO `customer` (`username`, `password`, `first_name`, `last_name`, `mobile_no`)
VALUES ('helen', 'helen123', 'Helen', 'Fonseka', '0755625183');
INSERT INTO `customer` (`username`, `password`, `first_name`, `last_name`, `mobile_no`)
VALUES ('ruks', 'rukshi123', 'Rukshi', 'Withana', '0772729729');
INSERT INTO `customer` (`username`, `password`, `first_name`, `last_name`, `mobile_no`)
VALUES ('nils', 'nilu123', 'Niluni', 'Fernela', '0762345123');
INSERT INTO `customer` (`username`, `password`, `first_name`, `last_name`, `mobile_no`)
VALUES ('thara', 'tharaka123', 'Tharaka', 'Perera', '0753476567');
INSERT INTO `customer` (`username`, `password`, `first_name`, `last_name`, `mobile_no`)
VALUES ('dumindu', 'dumi123', 'Dumindu', 'Niranga', '0727856154');
INSERT INTO `customer` (`username`, `password`, `first_name`, `last_name`, `mobile_no`)
VALUES ('hirusha', 'hirusha123', 'Hirusha', 'Thishan', '0777567234');


INSERT INTO `category`  VALUES (1,'Car');
INSERT INTO `category`  VALUES (2,'Tuk');
INSERT INTO `category`  VALUES (3,'Van');

INSERT INTO `vehicle` (`vehicle_no`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('2524847',3,8,'White');
INSERT INTO `vehicle` (`vehicle_no`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('AAB6032',1,3,'Red');
INSERT INTO `vehicle` (`vehicle_no`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('BAA3357',1,3,'Black');
INSERT INTO `vehicle` (`vehicle_no`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('BBC5678',1,3,'Black');
INSERT INTO `vehicle` (`vehicle_no`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('CAP7880',1,3,'Purple');
INSERT INTO `vehicle` (`vehicle_no`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('CAS3922',1,3,'Blue');
INSERT INTO `vehicle` (`vehicle_no`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('PK7256',3,5,'White');

-- INSERT INTO `driver`  VALUES (1,'2f7b52aacfbf6f44e13d27656ecb1f59','Thilak','Perera','645078526V','0764567123',1);
-- INSERT INTO `driver`  VALUES (2,'2f7b52aacfbf6f44e13d27656ecb1f59','Jagath','Fernando','724010586V','0757033546',1);
-- INSERT INTO `driver`  VALUES (3,'2f7b52aacfbf6f44e13d27656ecb1f59','Nevil','Almeida','683010488V','0727834567',1);
-- INSERT INTO `driver`  VALUES (4,'2f7b52aacfbf6f44e13d27656ecb1f59','Harry','Silva','703820333V','0777876702',2);
-- INSERT INTO `driver`  VALUES (5,'2f7b52aacfbf6f44e13d27656ecb1f59','Vimal','De Silva','759030543V','0719080633',4);
-- INSERT INTO `driver`  VALUES (6,'2f7b52aacfbf6f44e13d27656ecb1f59','Suneth','Perera','804022865V','0765566234',3);
-- INSERT INTO `driver`  VALUES (7,'2f7b52aacfbf6f44e13d27656ecb1f59','Kamal','Fernando','852055234V','0756070890',3);

INSERT INTO `driver`  VALUES (1,'2f7b52aacfbf6f44e13d27656ecb1f59','Thilak','Perera','645078526V','0764567123',1,'2524847');
INSERT INTO `driver`  VALUES (2,'2f7b52aacfbf6f44e13d27656ecb1f59','Jagath','Fernando','724010586V','0757033546',1,'AAB6032');
INSERT INTO `driver`  VALUES (3,'2f7b52aacfbf6f44e13d27656ecb1f59','Nevil','Almeida','683010488V','0727834567',1,'BAA3357');
INSERT INTO `driver`  VALUES (4,'2f7b52aacfbf6f44e13d27656ecb1f59','Harry','Silva','703820333V','0777876702',2,'BBC5678');
INSERT INTO `driver`  VALUES (5,'2f7b52aacfbf6f44e13d27656ecb1f59','Vimal','De Silva','759030543V','0719080633',4,'CAP7880');
INSERT INTO `driver`  VALUES (6,'2f7b52aacfbf6f44e13d27656ecb1f59','Suneth','Perera','804022865V','0765566234',3,'CAS3922');
INSERT INTO `driver`  VALUES (7,'2f7b52aacfbf6f44e13d27656ecb1f59','Kamal','Fernando','852055234V','0756070890',3,'PK7256');




-- INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('2524847',4,3,8,'White');
-- INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('AAB6032',1,1,3,'Red');
-- INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('BAA3357',5,1,3,'Black');
-- INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('BBC5678',6,1,3,'Black');
-- INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('CAP7880',3,1,3,'Purple');
-- INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('CAS3922',2,1,3,'Blue');
-- INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_category`,`noOfSeats`,`colour`) VALUES ('PK7256',7,3,5,'White');



INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('1', '2', '36.9');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('1', '3', '114.4');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('1', '4', '124.4');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('1', '5', '102.8');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('1', '6', '403.2');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('2', '3', '134.7');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('2', '4', '90.3');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('2', '5', '68.7');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('2', '6', '369.1');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('3', '4', '222.1');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('3', '5', '200.5');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('3', '6', '500.9');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('4', '5', '42.7');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('4', '6', '316.8');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('5', '6', '301.6');




