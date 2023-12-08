insert into homee(dashboardId) values (null);
insert into userhomee(userid, homeeid) values (2, 1);

INSERT INTO Room (title, powerStatus, notificationStatus, roomStatsId) VALUES
('Living Room', 1, 0, null),
('Bedroom', 1, 1, null),
('Kitchen', 0, 1, null),
('Bathroom', 1, 0, null);

INSERT INTO Device (devicename, powerStatus, notificationStatus, deviceStatsId) VALUES
('LED 1', 1, 0, null),
('LED 2', 1, 1, null),
('LED 3', 0, 1, null),
('TV', 1, 0, null);


use homee;

insert into users(firstName, lastname, birthDate, cnic, password) values ("Test", "1", "2010-12-25", "11111-1111111-1", "Test123");
insert into users(firstName, lastname, birthDate, cnic, password) values ("Ali", "Abbas", "2010-12-13", "12345-1234567-1", "11111");
select * from users;
select * from CollectiveStatistics;
select * from homee;
select * from dashboard;
select * from room;
select * from DashboardRoom;
select * from device; 
select * from statistics;

select hom.* from userhomee uhom 
join users usr on usr.id = uhom.userid
join homee hom on hom.id = uhom.homeeid
where uhom.userid = 2;

select * from homee h
join dashboard d on d.id = h.dashboardid;


select dash.* from homee hom 
join dashboard dash on dash.id = hom.dashboardid 
where hom.id = 1;