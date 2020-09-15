-- Create Database

DROP DATABASE IF EXISTS `Wacky_Walk_Database`;
CREATE DATABASE `Wacky_Walk_Database`;
USE `Wacky_Walk_Database`;

-- Create Tables

CREATE TABLE `customer` (
    `id` INTEGER AUTO_INCREMENT,
    `first_name` VARCHAR(100),
    `last_name` VARCHAR(100),
    `email` VARCHAR(200),
    `password`  VARCHAR(100),
    `registered_on` DATE,

    CONSTRAINT `pk_customer_id`
        PRIMARY KEY (`id`)
);

CREATE TABLE `product` (
    `id` INTEGER AUTO_INCREMENT,
    `name` VARCHAR(100),
    `description`  VARCHAR(100),
    `price` DECIMAL(5,2),
    `size` ENUM('0','1','2','3','4','5','6','7','8','9','10'),
    `colour` ENUM('Blue', 'Red', 'Green', 'Purple', 'Yellow', 'Black', 'White', 'Gold', 'Silver'),
    `picture` VARCHAR(500),
    `rating` INTEGER,

    CONSTRAINT `pk_product_id`
        PRIMARY KEY (`id`)
);

CREATE TABLE `review` (
    `product_id` INTEGER,
    `customer_id` INTEGER,
    `comment` VARCHAR(1000),
    `rating` INTEGER,
    `picture` BLOB,
    `video` BLOB,
    
    CONSTRAINT `pk_review_product_id_customer_id`
        PRIMARY KEY (`product_id`,`customer_id`),
    
    CONSTRAINT `fk_review_product_id`
        FOREIGN KEY (`product_id`)
        REFERENCES `product`(`id`),
    CONSTRAINT `fk_review_customer_id`
        FOREIGN KEY (`customer_id`)
        REFERENCES `customer`(`id`)
);

CREATE TABLE `store` (
    `id` INTEGER AUTO_INCREMENT,
    `store_name` VARCHAR(100),
    `country` VARCHAR(100),
    `city` VARCHAR(100),
    `postal_code` VARCHAR(25),
    `phone_number` INTEGER,

    CONSTRAINT `pk_store_id`
        PRIMARY KEY (`id`)
);

CREATE TABLE `order` (
    `id` INTEGER AUTO_INCREMENT,
    `customer_id` INTEGER,
    `date` DATETIME,
    `discount` ENUM('C00LSH0ES', 'ALMOSTFREE', 'IMPRESS123'),
    `total` DECIMAL(5,2),

    CONSTRAINT `pk_order_id`
        PRIMARY KEY (`id`),

    CONSTRAINT `fk_order_customer`
        FOREIGN KEY (`customer_id`)
        REFERENCES `customer`(`id`)
);

CREATE TABLE `cart` (
    `product_id` INTEGER,
    `customer_id` INTEGER,
    `quantity` INTEGER,
    
    CONSTRAINT `pk_cart_product_id_customer_id`
        PRIMARY KEY (`product_id`, `customer_id`),

    CONSTRAINT `fk_cart_product_id`
        FOREIGN KEY (`product_id`)
        REFERENCES `product`(`id`),
    CONSTRAINT `fk_cart_customer_id`
        FOREIGN KEY (`customer_id`)
        REFERENCES `customer`(`id`)
    
);

CREATE TABLE `product_store` (
    `product_id` INTEGER,
    `store_id` INTEGER,
    `quantity` INTEGER,

    CONSTRAINT `pk_products_store_product_id_store_id`
        PRIMARY KEY (`product_id`,`store_id`),

    CONSTRAINT `fk_product_store_product_id`
        FOREIGN KEY (`product_id`)
        REFERENCES `product`(`id`),
    CONSTRAINT `fk_product_store_store_id`
        FOREIGN KEY (`store_id`)
        REFERENCES `store`(`id`)
);

CREATE TABLE `order_item` (
    `product_id` INTEGER,
    `order_id` INTEGER,
    `store_id` INTEGER,
    `quantity` INTEGER,
    `total` DECIMAL(5,2),

    CONSTRAINT `pk_order_item_product_id_order_id`
        PRIMARY KEY (`product_id`,`order_id`),
        
    CONSTRAINT `fk_order_item_product_id`
        FOREIGN KEY (`product_id`)
        REFERENCES `product`(`id`),
    CONSTRAINT `fk_order_item_order_id`
        FOREIGN KEY (`order_id`)
        REFERENCES `order`(`id`),
    CONSTRAINT `fk_order_item_store_id`
        FOREIGN KEY (`store_id`)
        REFERENCES `store`(`id`)
);

CREATE TABLE `product_audit`(
    `id` INTEGER AUTO_INCREMENT,
    `product_id` INTEGER,
    `edit_date` DATE,

    CONSTRAINT `pk_product_audit_id`
        PRIMARY KEY (`id`),
    CONSTRAINT `fk_product_audit_product_id`
        FOREIGN KEY (`product_id`)
        REFERENCES `product` (`id`)
);

CREATE TABLE `store_audit`(
    `id` INTEGER AUTO_INCREMENT,
    `store_id` INTEGER,
    `edit_date` DATE,

    CONSTRAINT `pk_store_audit_id`
        PRIMARY KEY (`id`),
    CONSTRAINT `fk_store_audit_store_id`
        FOREIGN KEY (`store_id`)
        REFERENCES `store` (`id`)
);

CREATE TABLE `review_audit`(
    `id` INTEGER AUTO_INCREMENT,
    `product_id` INTEGER,
    `comment` VARCHAR(100),
    `added_date` DATE,

    CONSTRAINT `pk_review_audit_id`
        PRIMARY KEY (`id`),
    CONSTRAINT `fk_review_audit_product_id`
        FOREIGN KEY (`product_id`)
        REFERENCES `review` (`product_id`)
);

-- Create Views

CREATE VIEW `daily_profit` AS 
    SELECT SUM(`total`) AS 'Daily Income' 
    FROM `order` 
    WHERE `date` = CURDATE();

CREATE VIEW `quantity_sold_per_shoe_per_month` AS
    SELECT `name`, SUM(`quantity`) AS 'Quantity'
    FROM (`order_item` JOIN `order` ON `order_id`) JOIN `product` ON `product_id`
    Where (MONTH(`date`) = MONTH(CURDATE())) AND (YEAR(`date`) = YEAR(CURDATE()))
    GROUP BY `name`;

CREATE VIEW `top_10_rated_products` AS
    SELECT *
    FROM (SELECT `name`, AVG(`review`.`rating`) AS `ratings` 
    FROM `review` JOIN `product` ON `product_id` GROUP BY `product_id`) AS `average_rating`
    LIMIT 10;

-- Create Triggers

CREATE TRIGGER `log_product_entry` AFTER UPDATE ON `product`
    FOR EACH ROW
    INSERT INTO `product_audit`
    SET ACTION = 'update',
        `product_id` = `product`.`product_id`,
        `edit_date` = CURDATE();

CREATE TRIGGER `log_store_entry` AFTER UPDATE ON `store`
    FOR EACH ROW
    INSERT INTO `store_audit`
    SET ACTION = 'UPDATE',
        `store_id` = `store`.`store_id`,
        `edit_date` = CURDATE();

CREATE TRIGGER `log_review_date` AFTER UPDATE ON `review`
    FOR EACH ROW
    INSERT INTO `review_audit`
    SET ACTION = `review`.`update`,
        `product_id` = `review`.`product_id`,
        `comment` = `review`.`comment`,
        `added_date` = CURDATE();

-- Create Roles

DROP ROLE IF EXISTS `customer`, `admin`;
CREATE ROLE `customer`, `admin`;

-- Grant Role Permissions

GRANT ALL ON `Wacky_Walk_Database`.* TO `admin`;

GRANT SELECT ON `Wacky_Walk_Database`.product TO `customer`;
GRANT SELECT, INSERT ON `Wacky_Walk_Database`.review TO `customer`;
GRANT INSERT, UPDATE ON `Wacky_Walk_Database`.customer TO `customer`;
GRANT INSERT, DELETE, UPDATE ON `Wacky_Walk_Database`.cart TO `customer`;
GRANT INSERT ON `Wacky_Walk_Database`.order TO `customer`;

-- Insert Mock Data

INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (1,'skippers', 'n/a', 12.99, '1', 'Red', '/Assignment-3-HANN-PATEL/images/highHeelLadder.jpg', 4);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (2,'lowtops', 'n/a', 27.89, '3', 'White', '/Assignment-3-HANN-PATEL/images/boxing.jpg', 3);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (3,'breathers', 'n/a', 14.56, '7', 'Green', '/Assignment-3-HANN-PATEL/images/candyBag.jpg',5);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (4,'funkies', 'n/a', 31.99, '5', 'Purple', '/Assignment-3-HANN-PATEL/images/CrocConverse.jpg',2);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (5,'flyers', 'n/a', 76.23, '2', 'White', '/Assignment-3-HANN-PATEL/images/dog.jpg',4);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (6,'double layers', 'n/a', 45.99, '4', 'Black', '/Assignment-3-HANN-PATEL/images/duck.jpg',5);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (7,'double heel', 'n/a', 32.99, '3', 'Yellow', '/Assignment-3-HANN-PATEL/images/FishFers.jpg',3);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (8,'lits', 'n/a', 12.89, '7', 'Red', '/Assignment-3-HANN-PATEL/images/giraffe.jpg',4);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (9,'fleet footworks', 'n/a', 87.90, '9', 'Gold', '/Assignment-3-HANN-PATEL/images/hairyFeet.png',1);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (10,'water walking', 'n/a', 12.99, '0', 'Green', '/Assignment-3-HANN-PATEL/images/horn.jpg',2);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (11,'noice', 'n/a', 99.47, '7', 'Silver', '/Assignment-3-HANN-PATEL/images/insideFish.jpg',4);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (12,'cat', 'n/a', 48.54, '8', 'Blue', '/Assignment-3-HANN-PATEL/images/kfc.jpg',1);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (13,'walking', 'n/a', 834.90, '6', 'Green', '/Assignment-3-HANN-PATEL/images/mouth.jpg',1);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (14,'yes', 'n/a', 95.52, '8', 'Purple', '/Assignment-3-HANN-PATEL/images/nature.jpg',3);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (15,'rich', 'n/a', 455.45, '10', 'Gold', '/Assignment-3-HANN-PATEL/images/pencil.jpg',5);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (16,'no', 'n/a', 54.22, '3', 'Red', '/Assignment-3-HANN-PATEL/images/ratUnicorn.jpg',4);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (17,'fire', 'n/a', 85.23, '3', 'Yellow', '/Assignment-3-HANN-PATEL/images/sandwich.jpg',3);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (18,'air', 'n/a', 94.16, '0', 'Blue', '/Assignment-3-HANN-PATEL/images/swimmers.jpg',2);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (19,'apples', 'n/a', 23.21, '9', 'Red', '/Assignment-3-HANN-PATEL/images/tentacles.jpg',1);
INSERT INTO `product` (`id`, `name`, `description`, `price`, `size`, `colour`, `picture`, `rating`) 
    VALUES (20,'pet', 'n/a', 24.65, '5', 'Green', '/Assignment-3-HANN-PATEL/images/banana.jpg',5);


INSERT INTO `customer` (`id`, `first_name`, `last_name`, `email`, `password`, `registered_on`) 
    VALUES (1,'John', 'Lesley', 'lesley123@gmail.com', 'pinkApple43', '2019-01-23');

INSERT INTO `customer` (`id`, `first_name`, `last_name`, `email`, `password`, `registered_on`) 
    VALUES (2, 'Micheal', 'Dudley', 'applePear@fearless.co', 'greyGorrila54', '2020-03-12');

INSERT INTO `customer` (`id`, `first_name`, `last_name`, `email`, `password`, `registered_on`) 
    VALUES (3, 'Adam', 'Jeffreys', 'theRealOne98@yahoo.ca', 'theLegend27', '2018-06-23');


INSERT INTO `order` (`id`, `customer_id`, `date`, `discount`, `total`) 
    VALUES (1, 1, '2020-01-01', Null, 50.00);
INSERT INTO `order` (`id`, `customer_id`, `date`, `discount`, `total`) 
    VALUES (2, 1, '2020-02-12', Null, 34.78);
INSERT INTO `order` (`id`, `customer_id`, `date`, `discount`, `total`) 
    VALUES (3, 3, '2019-08-23', Null, 53.78);
INSERT INTO `order` (`id`, `customer_id`, `date`, `discount`, `total`) 
    VALUES (4, 2, '2020-04-12', Null, 65.98);
INSERT INTO `order` (`id`, `customer_id`, `date`, `discount`, `total`) 
    VALUES (5, 1, '2019-05-23', Null, 43.89);


INSERT INTO `store` (`id`, `store_name`, `country`, `city`, `postal_code`, `phone_number`) 
    VALUES (1, 'WALMART','Canada', 'Montreal', 'H8Y1L3', '1234567890');
INSERT INTO `store` (`id`, `store_name`, `country`, `city`, `postal_code`, `phone_number`) 
    VALUES (2, 'Foot Locker', 'Canada', 'Laval', 'T6Y7L5', '0987654321');
INSERT INTO `store` (`id`, `store_name`, `country`, `city`, `postal_code`, `phone_number`) 
    VALUES (3, 'Globo', 'Canada', 'Rimouski', 'J4L8Y7', '1234567809');
INSERT INTO `store` (`id`, `store_name`, `country`, `city`, `postal_code`, `phone_number`) 
    VALUES (4, 'Maxi', 'Canada', 'Rimouski', 'T6R5Y4', '123738213');
INSERT INTO `store` (`id`, `store_name`, `country`, `city`, `postal_code`, `phone_number`) 
    VALUES (5, 'Target', 'Canada', 'Rimouski', 'Y6T4T5', '1279346732    ');


INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (1, 1, 'GOOD!', 4, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (2, 1, 'BAD?', 1, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (3, 1, 'OK?!', 3, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (4, 1, 'could be better', 1, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (5, 1, 'best in the world', 3, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (6, 1, 'i hate this company', 4, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (7, 1, 'Vik is the best teacher', 5, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (8, 1, 'if you read this review let me know on discord', 0, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (9, 1, 'are you really reading all the reviews vik?', 2, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (10, 1, 'not the greatest', 4, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (11, 1, 'wow', 5, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (12, 1, 'would never by ever in my life', 1, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (13, 1, 'would be again', 4, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (14, 1, 'really confortable', 2, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (15, 1, 'iss ok', 3, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (16, 1, 'best', 2, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (17, 1, 'needs work', 3, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (18, 1, 'hello', 1, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (19, 1, 'this my second last review', 5, Null, Null);
INSERT INTO `review` (`product_id`, `customer_id`, `comment`, `rating`, `picture`, `video`) 
    VALUES (20, 1, 'this is my last review', 4, Null, Null);
   

INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (1, 2, 23);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (2, 2, 12);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (3, 2, 43);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (4, 1, 3);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (5, 1, 24);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (6, 1, 54);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (7, 3, 2);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (8, 3, 23);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (9, 3, 32);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (10, 3, 14);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (11, 3, 64);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (12, 1, 35);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (13, 1, 23);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (14, 3, 11);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (15, 2, 24);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (16, 2, 1);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (17, 1, 24);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (18, 3, 21);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (19, 2, 52);
INSERT INTO `product_store` (`product_id`, `store_id`, `quantity`) 
    VALUES (20, 1, 13);


INSERT INTO `order_item` (`product_id`, `order_id`, `store_id`, `quantity`, `total`) 
    VALUES (1, 1, 3, 2, 23.54);
INSERT INTO `order_item` (`product_id`, `order_id`, `store_id`, `quantity`, `total`) 
    VALUES (2, 1, 3, 2, 45.32);
INSERT INTO `order_item` (`product_id`, `order_id`, `store_id`, `quantity`, `total`) 
    VALUES (3, 1, 3,3, 45.76);

-- Create Indexes

CREATE INDEX `idx_review_rating` ON `review` (`rating`);

CREATE INDEX `idx_product_name` ON `product` (`name`);

CREATE INDEX `idx_product_store_quantity` ON `product_store` (`quantity`);

CREATE INDEX `idx_order_total` ON `order` (`total`);
