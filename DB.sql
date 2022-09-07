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
CREATE TABLE `driver`
(
    `driver_id`  int         NOT NULL,
    `first_name` varchar(45) NOT NULL,
    `last_name`  varchar(45) DEFAULT NULL,
    `NIC`        varchar(12) NOT NULL,
    `mobile`     varchar(10) NOT NULL,
    `branch_id`  int         NOT NULL,

    PRIMARY KEY (`driver_id`),
    KEY          `branch_id_idx` (`branch_id`),
    CONSTRAINT `branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`)
);
CREATE TABLE `vehicle`
(
    `vehicle_no`   varchar(45) NOT NULL,
    `driver_id`    int         NOT NULL,
    `vehicle_type` varchar(45) NOT NULL,
    `noOfSeats`    int DEFAULT NULL,
    `colour`       varchar(45) NOT NULL,
    PRIMARY KEY (`vehicle_no`),
    KEY            `vehi_driver_idx` (`driver_id`),
    CONSTRAINT `vehi_driver` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
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


INSERT INTO `driver` (`driver_id`,`first_name`,`last_name`,`NIC`,`mobile`,`branch_id`) VALUES (1,'Thilak','Perera','645078526V','0764567123',1);
INSERT INTO `driver` (`driver_id`,`first_name`,`last_name`,`NIC`,`mobile`,`branch_id`) VALUES (2,'Jagath','Fernando','724010586V','0757033546',1);
INSERT INTO `driver` (`driver_id`,`first_name`,`last_name`,`NIC`,`mobile`,`branch_id`) VALUES (3,'Nevil','Almeida','683010488V','0727834567',1);
INSERT INTO `driver` (`driver_id`,`first_name`,`last_name`,`NIC`,`mobile`,`branch_id`) VALUES (4,'Harry','Silva','703820333V','0777876702',2);
INSERT INTO `driver` (`driver_id`,`first_name`,`last_name`,`NIC`,`mobile`,`branch_id`) VALUES (5,'Vimal','De Silva','759030543V','0719080633',4);
INSERT INTO `driver` (`driver_id`,`first_name`,`last_name`,`NIC`,`mobile`,`branch_id`) VALUES (6,'Suneth','Perera','804022865V','0765566234',3);
INSERT INTO `driver` (`driver_id`,`first_name`,`last_name`,`NIC`,`mobile`,`branch_id`) VALUES (7,'Kamal','Fernando','852055234V','0756070890',3);


INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_type`,`noOfSeats`,`colour`) VALUES ('2524847',4,'Van',8,'White');
INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_type`,`noOfSeats`,`colour`) VALUES ('AAB6032',1,'Car',3,'Red');
INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_type`,`noOfSeats`,`colour`) VALUES ('BAA3357',5,'Car',3,'Black');
INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_type`,`noOfSeats`,`colour`) VALUES ('BBC5678',6,'Car',3,'Black');
INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_type`,`noOfSeats`,`colour`) VALUES ('CAP7880',3,'Car',3,'Purple');
INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_type`,`noOfSeats`,`colour`) VALUES ('CAS3922',2,'Car',3,'Blue');
INSERT INTO `vehicle` (`vehicle_no`,`driver_id`,`vehicle_type`,`noOfSeats`,`colour`) VALUES ('PK7256',7,'van',5,'White');


INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('1', '1', '0.0');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('1', '2', '36.9');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('1', '3', '114.4');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('1', '4', '124.4');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('1', '5', '102.8');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('1', '6', '403.2');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('2', '2', '0.0');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('2', '3', '134.7');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('2', '4', '90.3');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('2', '5', '68.7');
INSERT INTO `go_cheeta`.`distance` (`distance_one`, `distance_two`, `distance`) VALUES ('2', '6', '369.1');


-- SELECT * FROM go_cheeta.driver WHERE branch_id=1 AND driver_id NOT IN (SELECT driver_id FROM `order` WHERE booking_state <=2 );