drop database homee;
create database homee;
use homee;

CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    birthDate DATE,
    cnic VARCHAR(15) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE Statistics (
    id INT PRIMARY KEY AUTO_INCREMENT,
    powerUsage DOUBLE,
    powerSaved DOUBLE,
    powerSource VARCHAR(255)
);

CREATE TABLE Room (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    powerStatus BIT,
    notificationStatus BIT,
    roomStatsId INT,
    FOREIGN KEY (roomStatsId) REFERENCES Statistics(id)
);

INSERT INTO Room (title, powerStatus, notificationStatus, roomStatsId) VALUES
('Living Room', 1, 0, null),
('Bedroom', 1, 1, null),
('Kitchen', 0, 1, null),
('Bathroom', 1, 0, null);


CREATE TABLE CollectiveStatistics (
    id INT PRIMARY KEY AUTO_INCREMENT,
    powerUsage DOUBLE,
    powerSaved DOUBLE,
    powerSource VARCHAR(255)
);

CREATE TABLE Device (
    id INT PRIMARY KEY AUTO_INCREMENT,
    devicename varchar(255),
    powerstatus bit,
    notificationstatus bit,
    deviceStatsId INT,
    FOREIGN KEY (deviceStatsId) REFERENCES Statistics(id)
);

CREATE TABLE CollectiveStatistics_Device (
    collectiveStatisticsId INT,
    deviceId INT,
    PRIMARY KEY (collectiveStatisticsId, deviceId),
    FOREIGN KEY (collectiveStatisticsId) REFERENCES CollectiveStatistics(id),
    FOREIGN KEY (deviceId) REFERENCES Device(id)
);

CREATE TABLE Dashboard (
    id INT PRIMARY KEY AUTO_INCREMENT,
    powerMode VARCHAR(255),
    fullStatsId INT,
    FOREIGN KEY (fullStatsId) REFERENCES CollectiveStatistics(id)
);

CREATE TABLE Room_Dashboard (
    roomId INT,
    dashboardId INT,
    PRIMARY KEY (roomId, dashboardId),
    FOREIGN KEY (roomId) REFERENCES Room(id),
    FOREIGN KEY (dashboardId) REFERENCES Dashboard(id)
);


CREATE TABLE customer_support (
    tokenNumber INT AUTO_INCREMENT PRIMARY KEY,
    query TEXT,
    reportDate DATETIME,
    closeDate DATETIME,
    queryStatus BOOLEAN
);

CREATE TABLE homee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dashboardId int,
    FOREIGN KEY (dashboardId) REFERENCES Dashboard(id)
);



CREATE TABLE payment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE,
    description VARCHAR(255),
    paymentMethod VARCHAR(255),
    paymentDate DATETIME
);
