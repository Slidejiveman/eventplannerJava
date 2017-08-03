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
INSERT INTO `user`(`user_is_active`, `user_name`, `user_password`,
                   `user_username`,  `user_company` )
VALUES (1, 'Trevor Belmont', 'user1', 'vampire_killer', 1);
INSERT INTO `user`(`user_is_active`, `user_name`, `user_password`,
                   `user_username`,  `user_company` )
VALUES (1, 'Andy Harbert', '2hosed_zombies!', 'joeh', 1);
INSERT INTO `user`(`user_is_active`, `user_name`, `user_password`,
                   `user_username`,  `user_company` )
VALUES (1, 'Plain Jane', 'n0t_really@plain', 'planejane', 1);

-- Insert the role assignments for the users. This is an association table
-- that assumes a user may have muliple roles.
INSERT INTO `roleassignment` (`role`, `roleassignment_user_id`)
VALUES('Standard', 1);
INSERT INTO `roleassignment` (`role`, `roleassignment_user_id`)
VALUES('Administrator', 2);
INSERT INTO `roleassignment` (`role`, `roleassignment_user_id`)
VALUES('Standard', 3);

-- Insert the default guest lists. Guests will be associated with these
-- Guest List IDs are 1 and 2
-- NOTE: It would probably be good to add in a reference to the Event.
INSERT INTO `guestlist`(`guestlist_size`)
VALUES (3);
INSERT INTO `guestlist`(`guestlist_size`)
VALUES (6);

-- Insert the default Guests. There needs to be 9 to match the guestlist sizes.
-- The first 3 guests should be associated with event 1, which should have
-- table size of 2. The first two guests are associated with table 1.
INSERT INTO `guest`(`guest_name`, `guest_relationship_descriptor`,
                    `guest_guestlist`, `guest_table_id`)
VALUES ('Jon Jonz', 'Father of the Bride', 1, 1); -- 1
INSERT INTO `guest`(`guest_name`, `guest_relationship_descriptor`,
                    `guest_guestlist`, `guest_table_id`)
VALUES ('Jane Jonz', 'Mother of the Bride', 1, 1); -- 2
INSERT INTO `guest`(`guest_name`, `guest_relationship_descriptor`,
                    `guest_guestlist`, `guest_table_id`)
VALUES ('Dolph Mulligan', 'Some Random Guy', 1, 2); -- 3
-- The guests for event two, associated with guestlist 2. Here, There
-- Will also be two tables of size 4. This will leave 2 unused seats.
INSERT INTO `guest`(`guest_name`, `guest_relationship_descriptor`,
                    `guest_guestlist`, `guest_table_id`)
VALUES ('Krusty the Klown', 'Sells Hamburgers', 2, 1); -- 4
INSERT INTO `guest`(`guest_name`, `guest_relationship_descriptor`,
                    `guest_guestlist`, `guest_table_id`)
VALUES ('Bart Simpson', 'Skateboards near Homer', 2, 1); -- 5
INSERT INTO `guest`(`guest_name`, `guest_relationship_descriptor`,
                    `guest_guestlist`, `guest_table_id`)
VALUES ('Homer Simpson', 'Eats Hamburgers', 2, 1); -- 6
INSERT INTO `guest`(`guest_name`, `guest_relationship_descriptor`,
                    `guest_guestlist`, `guest_table_id`)
VALUES ('Hank Hill', 'Sells propane and propane accessories', 2, 2); -- 7
INSERT INTO `guest`(`guest_name`, `guest_relationship_descriptor`,
                    `guest_guestlist`, `guest_table_id`)
VALUES ('Peggy Hill', 'Makes Spapeggy and meatballs', 2, 2); -- 8
INSERT INTO `guest`(`guest_name`, `guest_relationship_descriptor`,
                    `guest_guestlist`, `guest_table_id`)
VALUES ('Bobby Hill', 'Got dang it, Bobby!', 2, 2); -- 9

-- These bridge entities may change: auto_increment issue? Need Event?
-- Guest to sit with bridge table
-- The initial section will handle the first 3 guests for event 1
INSERT INTO `guesttositwith`(`guest_id`, `guest_sitwith_id`)
VALUES (1,2); -- Jon sits with Jane
INSERT INTO `guesttositwith`(`guest_id`, `guest_sitwith_id`)
VALUES (2,1); -- Jane sits with Jon
INSERT INTO `guesttositwith`(`guest_id`, `guest_sitwith_id`)
VALUES (6,5); -- Homer sits with Bart
INSERT INTO `guesttositwith`(`guest_id`, `guest_sitwith_id`)
VALUES (5,6); -- Bart sits with Homer
INSERT INTO `guesttositwith`(`guest_id`, `guest_sitwith_id`)
VALUES (7,8); -- Hank sits with Peggy
INSERT INTO `guesttositwith`(`guest_id`, `guest_sitwith_id`)
VALUES (8,9); -- Peggy sits with Bobby

-- Guest to avoid bridge table
INSERT INTO `guesttoavoid`(`guest_avoid_id`, `guest_id`)
VALUES (3,1); -- Jon avoids Dolph
INSERT INTO `guesttoavoid`(`guest_avoid_id`, `guest_id`)
VALUES (6,7); -- Hank avoids Homer

-- Seating Arrangements to load by default in the database
-- Creates a 1 and a 2 which will be associated with the events
INSERT INTO `seatingarrangement`()
VALUES(); -- 1
INSERT INTO `seatingarrangement`()
VALUES(); -- 2

-- Default Tables to load into the system
-- Event 1 and 2 have 2 tables each. Event 1 has size 2
-- Event 2 has size 4.
INSERT INTO `eventtable`(`table_number`, `table_shape`, `table_size`,
                    `table_event_id`, `table_seatingarrangement_id`)
VALUES (1,'Square','Two',1,1);
INSERT INTO `eventtable`(`table_number`, `table_shape`, `table_size`,
                    `table_event_id`, `table_seatingarrangement_id`)
VALUES (2,'Square','Two',1,1);
INSERT INTO `eventtable`(`table_number`, `table_shape`, `table_size`,
                    `table_event_id`, `table_seatingarrangement_id`)
VALUES (1,'Circle','Four',2,2);
INSERT INTO `eventtable`(`table_number`, `table_shape`, `table_size`,
                    `table_event_id`, `table_seatingarrangement_id`)
VALUES (2,'Circle','Four',2,2);

-- Default Events to add into the system
INSERT INTO `event`(`event_date`, `event_status`, `event_location`,
                    `event_menu`, `event_name`, `event_percent_seats_empty`,
                    `event_total_seats`, `event_user_id`, `event_customer_id`,
                    `event_guestlist`, `event_seatingarrangement`)
VALUES ('2010-11-12','Open','The PEC','Hot Dogs','Slamfest',10.0,4,1,1,1,1);
INSERT INTO `event`(`event_date`, `event_status`, `event_location`,
                    `event_menu`, `event_name`, `event_percent_seats_empty`,
                    `event_total_seats`, `event_user_id`, `event_customer_id`,
                    `event_guestlist`, `event_seatingarrangement`)
VALUES ('2010-10-11','Open','The World','Easy Mac','Cheeze-a-palooaz',25.0,8,2,
        2,2,2);

-- Tokens may not make sense in the script.
-- INSERT INTO `token`(`expire_date`, `token`, `user_id`)
-- VALUES ('2018-01-01', 'abcdefghijklmnopqrstuvwxyz', 2)

-- Turn the FOREIGN_KEY_CHECKS back on so the database
-- uses this data integrity feature.
SET FOREIGN_KEY_CHECKS=1;
