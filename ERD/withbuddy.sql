SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS chat_db;
DROP TABLE IF EXISTS acceptList_db;
DROP TABLE IF EXISTS adminBlacklist_db;
DROP TABLE IF EXISTS banList_db;
DROP TABLE IF EXISTS buddy_db;
DROP TABLE IF EXISTS whoau_db;
DROP TABLE IF EXISTS like_db;
DROP TABLE IF EXISTS map_db;
DROP TABLE IF EXISTS marker_db;
DROP TABLE IF EXISTS markerIcon_db;
DROP TABLE IF EXISTS reporter_db;
DROP TABLE IF EXISTS report_db;
DROP TABLE IF EXISTS user_db;




/* Create Tables */

CREATE TABLE acceptList_db
(
	acceptId int NOT NULL AUTO_INCREMENT,
	Id int NOT NULL,
	mateId int NOT NULL,
	PRIMARY KEY (acceptId, Id)
);


CREATE TABLE adminBlacklist_db
(
	blackListId int NOT NULL AUTO_INCREMENT,
	Id int NOT NULL,
	PRIMARY KEY (blackListId, Id)
);


CREATE TABLE banList_db
(
	banId int NOT NULL AUTO_INCREMENT,
	Id int NOT NULL,
	baneduserId int NOT NULL,
	PRIMARY KEY (banId, Id)
);


CREATE TABLE buddy_db
(
	buddyId int NOT NULL AUTO_INCREMENT,
	Id int NOT NULL,
	category varchar(50) NOT NULL,
	buddyName varchar(50) NOT NULL,
	buddyAge int,
	buddyImage blob NOT NULL,
	buddyDetail longtext,
	buddySex boolean NOT NULL,
	PRIMARY KEY (buddyId, Id)
);


CREATE TABLE chat_db
(
	id int NOT NULL AUTO_INCREMENT,
	acceptId int NOT NULL,
	Id int NOT NULL,
	message varchar(200),
	PRIMARY KEY (id)
);


CREATE TABLE like_db
(
	likeId int NOT NULL AUTO_INCREMENT,
	Id int NOT NULL,
	PRIMARY KEY (likeId)
);


CREATE TABLE map_db
(
	mapId int NOT NULL AUTO_INCREMENT,
	region varchar(50) NOT NULL,
	town varchar(50) NOT NULL,
	count int NOT NULL DEFAULT 0,
	map_X varchar(100) NOT NULL,
	map_Y varchar(100) NOT NULL,
	PRIMARY KEY (mapId)
);


CREATE TABLE markerIcon_db
(
	mkIconId int NOT NULL AUTO_INCREMENT,
	mkImage blob NOT NULL,
	PRIMARY KEY (mkIconId)
);


CREATE TABLE marker_db
(
	markerId int NOT NULL AUTO_INCREMENT,
	mkIconId int NOT NULL,
	location_X varchar(100) NOT NULL,
	location_Y varchar(100) NOT NULL,
	markerName varchar(100) NOT NULL,
	PRIMARY KEY (markerId)
);


CREATE TABLE reporter_db
(
	reporterId int NOT NULL,
	reportId int NOT NULL,
	Id int NOT NULL,
	rpContent longtext NOT NULL
);


CREATE TABLE report_db
(
	reportId int NOT NULL AUTO_INCREMENT,
	Id int NOT NULL,
	PRIMARY KEY (reportId, Id)
);


CREATE TABLE user_db
(
	Id int NOT NULL AUTO_INCREMENT,
	userName varchar(200) NOT NULL,
	password varchar(100) NOT NULL,
	phone varchar(20) NOT NULL,
	email varchar(100) NOT NULL,
	regionAddress varchar(100) NOT NULL,
	reportCount int,
	townAddress varchar(100) NOT NULL,
	PRIMARY KEY (Id),
	UNIQUE (userName),
	UNIQUE (email)
);


CREATE TABLE whoau_db
(
	likeId int NOT NULL,
	otherId int NOT NULL,
	PRIMARY KEY (likeId, otherId)
);



/* Create Foreign Keys */

ALTER TABLE chat_db
	ADD FOREIGN KEY (acceptId, Id)
	REFERENCES acceptList_db (acceptId, Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE whoau_db
	ADD FOREIGN KEY (likeId)
	REFERENCES like_db (likeId)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE marker_db
	ADD FOREIGN KEY (mkIconId)
	REFERENCES markerIcon_db (mkIconId)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reporter_db
	ADD FOREIGN KEY (reportId, Id)
	REFERENCES report_db (reportId, Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE acceptList_db
	ADD FOREIGN KEY (mateId)
	REFERENCES user_db (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE acceptList_db
	ADD FOREIGN KEY (Id)
	REFERENCES user_db (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE adminBlacklist_db
	ADD FOREIGN KEY (Id)
	REFERENCES user_db (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE banList_db
	ADD FOREIGN KEY (Id)
	REFERENCES user_db (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE banList_db
	ADD FOREIGN KEY (baneduserId)
	REFERENCES user_db (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE buddy_db
	ADD FOREIGN KEY (Id)
	REFERENCES user_db (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE like_db
	ADD FOREIGN KEY (Id)
	REFERENCES user_db (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reporter_db
	ADD FOREIGN KEY (reporterId)
	REFERENCES user_db (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE report_db
	ADD FOREIGN KEY (Id)
	REFERENCES user_db (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE whoau_db
	ADD FOREIGN KEY (otherId)
	REFERENCES user_db (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



