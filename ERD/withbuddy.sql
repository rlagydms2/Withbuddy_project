SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS chat_db;
alter table chat_db AUTO_INCREMENT = 1;
DROP TABLE IF EXISTS acceptList_db;
alter table acceptlist_db AUTO_INCREMENT = 1;
alter table banlist_db AUTO_INCREMENT = 1;
DROP TABLE IF EXISTS buddy_db;
alter table buddy_db AUTO_INCREMENT = 1;
DROP TABLE IF EXISTS user_db;
alter table user_db AUTO_INCREMENT = 1;
DROP TABLE IF EXISTS address_db;
alter table address_db AUTO_INCREMENT = 1;
DROP TABLE IF EXISTS Authority_db;
alter table authority_db AUTO_INCREMENT = 1;
DROP TABLE IF EXISTS map_db;
alter table map_db AUTO_INCREMENT = 1;
DROP TABLE IF EXISTS marker_db;
alter table marker_db AUTO_INCREMENT = 1;
DROP TABLE IF EXISTS markerIcon_db;
alter table markericon_db AUTO_INCREMENT = 1;






/* Create Tables */

CREATE TABLE acceptList_db
(
	acceptId int NOT NULL AUTO_INCREMENT,
	id int NOT NULL,
	mateId int NOT NULL,
	PRIMARY KEY (acceptId, id)
);


CREATE TABLE address_db
(
	id int NOT NULL AUTO_INCREMENT,
	addressName varchar(50) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (addressName)
);


CREATE TABLE Authority_db
(
	id int NOT NULL AUTO_INCREMENT,
	authorityName varchar(20) NOT NULL,
	PRIMARY KEY (id)
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
	phone varchar(20) NOT NULL,
	email varchar(100) NOT NULL,
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


ALTER TABLE userList
	ADD FOREIGN KEY (userId)
	REFERENCES user_db (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



