drop database homee;
create database homee;
use homee;

CREATE TABLE CollectiveStatistics (
    id INT PRIMARY KEY AUTO_INCREMENT,
    powerUsage DOUBLE,
    temperature DOUBLE,
    humidity DOUBLE
);

CREATE TABLE Dashboard (
    id INT PRIMARY KEY AUTO_INCREMENT,
    powerMode VARCHAR(255),
    fullStatsId INT,
    FOREIGN KEY (fullStatsId) REFERENCES CollectiveStatistics(id) ON DELETE CASCADE
);

CREATE TABLE homee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dashboardId int,
    title varchar(255),
    FOREIGN KEY (dashboardId) REFERENCES Dashboard(id) ON DELETE CASCADE
);

create table network (
	id  INT AUTO_INCREMENT PRIMARY KEY,
	title varchar(255),
	access varchar(255),
	ip varchar(12),
	live BIT
);

create table HomeeNet (
	netid int,
    homeeid int,
    primary key (netid, homeeid),
	FOREIGN KEY (netid) REFERENCES network(id) on delete cascade,
    FOREIGN KEY (homeeid) REFERENCES Homee(id) on delete cascade
);

CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    birthDate DATE,
    cnic VARCHAR(15) UNIQUE,
    password VARCHAR(255)
);

create table UserHomee (
	userId int,
    homeeId int,
    primary key (userId, homeeId),
    FOREIGN KEY (userId) REFERENCES Users(id) on delete cascade,
    FOREIGN KEY (homeeId) REFERENCES Homee(id) on delete cascade
);

CREATE TABLE Room (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    powerStatus BIT,
    notificationStatus BIT
);

CREATE TABLE DashboardRoom (
    roomId INT,
    dashboardId INT,
    PRIMARY KEY (roomId, dashboardId),
    FOREIGN KEY (roomId) REFERENCES Room(id) on delete cascade,
    FOREIGN KEY (dashboardId) REFERENCES Dashboard(id) on delete cascade
);

CREATE TABLE Statistics (
    id INT PRIMARY KEY AUTO_INCREMENT,
    powerUsage DOUBLE,
    temperature DOUBLE,
    humidity DOUBLE
);

CREATE TABLE Device (
    id INT PRIMARY KEY AUTO_INCREMENT,
    deviceNo INT,
    devicename varchar(255),
    powerstatus bit,
    notificationstatus bit,
    deviceStatsId INT,
    FOREIGN KEY (deviceStatsId) REFERENCES Statistics(id) ON DELETE CASCADE
);

CREATE TABLE RoomDevice (
    roomId INT,
    deviceId INT,
    PRIMARY KEY (roomId, deviceId),
    FOREIGN KEY (roomId) REFERENCES Room(id) on delete cascade,
    FOREIGN KEY (deviceId) REFERENCES Device(id) on delete cascade
);


CREATE TABLE customer_support (
    tokenNumber INT AUTO_INCREMENT PRIMARY KEY,
    query TEXT,
    reportDate DATETIME,
    closeDate DATETIME,
    queryStatus BOOLEAN
);


CREATE TABLE payment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE,
    description VARCHAR(255),
    paymentMethod VARCHAR(255),
    paymentDate DATETIME
);
