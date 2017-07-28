-- This Database initialization script fills the database tables for our
-- Software Engineering Projects 2 capstone class.
-- Note: All of these tables have AUTO_INCREMENT primary keys.
-- Author: Ryder Dale Walton
-- Date: 7/27/2017

-- Turn off FOREIGN_KEY_CHECKS so that the tables can be
-- populated with dummy data in any order.
SET FOREIGN_KEY_CHECKS=0;

-- Insert the default company into the database.
-- This is the only company needed for the current project.
-- Eagle Event Planning's ID number will be 1.
INSERT INTO `company`(`company_name`)
VALUES ('Eagle Event Planning');

-- Insert the default customers into the database.
INSERT INTO `customer`(`customer_email`, `customer_name`, `customer_phone`)
VALUES ('jackcandle79@gmail.com', 'Jack Candle', '555-123-4567');
INSERT INTO `customer`(`customer_email`, `customer_name`, `customer_phone`)
VALUES ('lowballlynch@gmail.com', 'Low, Ball, & Lynch', '555-765-4321');
INSERT INTO `customer`(`customer_email`, `customer_name`, `customer_phone`)
VALUES ('deweycheatumnhowe1@gmail.com', 'Dewey, Cheatum, & Howe', '555-213-6745');

-- Insert the default users into the database.
-- References company_id 1, Eagle Event Planning
INSERT INTO `user`(`user_is_active`, `user_name`, `user_password`, `user_role`,
                   `user_username`,  `user_company` )
VALUES (1, 'Trevor Belmont', 'user1', 'Standard', 'vampire_killer', 1);
INSERT INTO `user`(`user_is_active`, `user_name`, `user_password`, `user_role`,
                   `user_username`,  `user_company` )
VALUES (1, 'Andy Harbert', '2hosed_zombies!', 'Administrator', 'joeh', 1);
INSERT INTO `user`(`user_is_active`, `user_name`, `user_password`, `user_role`,
                   `user_username`,  `user_company` )
VALUES (1, 'Plain Jane', 'n0t_really@plain', 'Standard', 'planejane', 1);

-- Insert the default guest lists. Guests will be associated with these
-- Guest List IDs are 1 and 2
-- NOTE: It would probably be good to add in a reference to the Event.
INSERT INTO `guestlist`(`guestlist_size`)
VALUES (3);
INSERT INTO `guestlist`(`guestlist_size`)
VALUES (6);

-- Turn the FOREIGN_KEY_CHECKS back on so the database
-- uses this data integrity feature.
SET FOREIGN_KEY_CHECKS=1;
