SET SESSION FOREIGN_KEY_CHECKS=0;


/* Drop Tables */

DROP TABLE IF EXISTS map_db;
DROP TABLE IF EXISTS adminBlacklist_db;
DROP TABLE IF EXISTS banList_db;
DROP TABLE IF EXISTS buddy_db;
DROP TABLE IF EXISTS chat_db;
DROP TABLE IF EXISTS match_db;
DROP TABLE IF EXISTS reporter_db;
DROP TABLE IF EXISTS report_db;
DROP TABLE IF EXISTS userList;
DROP TABLE IF EXISTS user_db;
DROP TABLE IF EXISTS address_db;
DROP TABLE IF EXISTS Authority_db;
DROP TABLE IF EXISTS chatRoom_db;
DROP TABLE IF EXISTS marker_db;
DROP TABLE IF EXISTS markerIcon_db;




/* Create Tables */

CREATE TABLE address_db
(
	id int NOT NULL AUTO_INCREMENT,
	addressName varchar(50) NOT NULL,
	count int NOT NULL DEFAULT 0,
	PRIMARY KEY (id),
	UNIQUE (addressName)
);


CREATE TABLE adminBlacklist_db
(
	blackListId int NOT NULL AUTO_INCREMENT,
	id int NOT NULL,
	PRIMARY KEY (blackListId, id)
);


CREATE TABLE Authority_db
(
	id int NOT NULL AUTO_INCREMENT,
	authorityName varchar(20) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE banList_db
(
	banId int NOT NULL AUTO_INCREMENT,
	id int NOT NULL,
	baneduserId int NOT NULL,
	PRIMARY KEY (banId, id)
);


CREATE TABLE buddy_db
(
	buddyId int NOT NULL AUTO_INCREMENT,
	id int NOT NULL,
	category varchar(50) NOT NULL,
	buddyName varchar(50) NOT NULL,
	buddyAge int,
	buddyImage varchar(100),
	buddyDetail longtext,
	buddySex int NOT NULL,
	PRIMARY KEY (buddyId, id)
);


CREATE TABLE chatRoom_db
(
	roomId varchar(255) NOT NULL,
	PRIMARY KEY (roomId),
	UNIQUE (roomId)
);


CREATE TABLE chat_db
(
	chatId int NOT NULL AUTO_INCREMENT,
	roomId varchar(255) NOT NULL,
	senderId int NOT NULL,
	message varchar(200),
	sendTime datetime,
	PRIMARY KEY (chatId)
);


CREATE TABLE map_db
(
	mapId int NOT NULL AUTO_INCREMENT,
	addressId int NOT NULL,
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


CREATE TABLE match_db
(
	matchId int NOT NULL,
	senderId int NOT NULL,
	receiverId int NOT NULL,
	accept boolean,
	PRIMARY KEY (matchId)
);


CREATE TABLE reporter_db
(
	reportId int NOT NULL,
	userId int NOT NULL,
	repotedId int NOT NULL,
	rpContent longtext NOT NULL
);


CREATE TABLE report_db
(
	reportId int NOT NULL AUTO_INCREMENT,
	userId int NOT NULL,
	PRIMARY KEY (reportId, userId)
);


CREATE TABLE userList
(
	roomId varchar(255) NOT NULL,
	userId int NOT NULL,
	PRIMARY KEY (roomId, userId)
);


CREATE TABLE user_db
(
	id int NOT NULL AUTO_INCREMENT,
	authorityId int NOT NULL DEFAULT 1,
	addressId int,
	userId varchar(200) NOT NULL,
	password varchar(100) NOT NULL,
	phone varchar(20) ,
	email varchar(100) ,
	reportCount int,
	PRIMARY KEY (id),
	UNIQUE (userId),
	UNIQUE (email)
);



/* Create Foreign Keys */

ALTER TABLE map_db
	ADD FOREIGN KEY (addressId)
	REFERENCES address_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE user_db
	ADD FOREIGN KEY (addressId)
	REFERENCES address_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE user_db
	ADD FOREIGN KEY (authorityId)
	REFERENCES Authority_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE chat_db
	ADD FOREIGN KEY (roomId)
	REFERENCES chatRoom_db (roomId)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE userList
	ADD FOREIGN KEY (roomId)
	REFERENCES chatRoom_db (roomId)
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
	ADD FOREIGN KEY (reportId, userId)
	REFERENCES report_db (reportId, userId)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE adminBlacklist_db
	ADD FOREIGN KEY (id)
	REFERENCES user_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE banList_db
	ADD FOREIGN KEY (id)
	REFERENCES user_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE banList_db
	ADD FOREIGN KEY (baneduserId)
	REFERENCES user_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE buddy_db
	ADD FOREIGN KEY (id)
	REFERENCES user_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE chat_db
	ADD FOREIGN KEY (senderId)
	REFERENCES user_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE match_db
	ADD FOREIGN KEY (senderId)
	REFERENCES user_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE match_db
	ADD FOREIGN KEY (receiverId)
	REFERENCES user_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reporter_db
	ADD FOREIGN KEY (repotedId)
	REFERENCES user_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE report_db
	ADD FOREIGN KEY (userId)
	REFERENCES user_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE userList
	ADD FOREIGN KEY (userId)
	REFERENCES user_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



