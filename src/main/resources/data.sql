/**
 * CREATE Script for init of DB
 */
-- Create 3 Car Manufacturers
insert into manufacturer (id, date_created, deleted, brand,country_registered) values (1, now(), false, 'BMW','GER');
insert into manufacturer (id, date_created, deleted, brand,country_registered) values (2, now(), false, 'HONDA','JAP');
insert into manufacturer (id, date_created, deleted, brand,country_registered) values (3, now(), false, 'FORD','USA');
insert into manufacturer (id, date_created, deleted, brand,country_registered) values (4, now(), false, 'TATA','INDIA');
-- Create 4 Cars
insert into car (id, date_created, deleted, seat_count, registration_date,rating,model,manufacturer_id,license_plate,engine_type,classification,colour)  values (1, now(), false, 5,now(), 3,'X5',1,'IN1234','GAS','SUV','RED');
insert into car (id, date_created, deleted, seat_count, registration_date,rating,model,manufacturer_id,license_plate,engine_type,classification,colour)  values (2, now(), false, 5,now(), 2,'CIVIC',2,'IN1235','HYBRID','SEDAN','BLACK');
insert into car (id, date_created, deleted, seat_count, registration_date,rating,model,manufacturer_id,license_plate,engine_type,classification,colour)  values (3, now(), false, 5,now(), 0,'TIAGO',4,'IN1236','DIESEL','HATCHBACK','CYAN');
insert into car (id, date_created, deleted, seat_count, registration_date,rating,model,manufacturer_id,license_plate,engine_type,classification,colour)  values (4, now(), false, 5,now(), 0,'FOCUS',3,'IN1237','ELECTRIC','SUV','BLUE');
-- Create 3 OFFLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username,selected_car) values (1, now(), false, 'OFFLINE',
'driver01pw', 'driver01',2);

insert into driver (id, date_created, deleted, online_status, password, username) values (2, now(), false, 'OFFLINE',
'driver02pw', 'driver02');

insert into driver (id, date_created, deleted, online_status, password, username) values (3, now(), false, 'OFFLINE',
'driver03pw', 'driver03');


-- Create 3 ONLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username,selected_car) values (4, now(), false, 'ONLINE',
'driver04pw', 'driver04',1);

insert into driver (id, date_created, deleted, online_status, password, username) values (5, now(), false, 'ONLINE',
'driver05pw', 'driver05');

insert into driver (id, date_created, deleted, online_status, password, username) values (6, now(), false, 'ONLINE',
'driver06pw', 'driver06');

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'OFFLINE',
'driver07pw', 'driver07');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ONLINE',
'driver08pw', 'driver08');

