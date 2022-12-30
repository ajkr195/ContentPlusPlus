drop database if exists contentplusplus;
create database contentplusplus;
use contentplusplus;

create table app_user(
id BIGINT NOT NULL AUTO_INCREMENT,
useremail VARCHAR(150) NOT NULL,
userpassword VARCHAR(150) NOT NULL,
useruuid VARCHAR(50) NOT NULL,
userfirstname VARCHAR(150) NOT NULL,
userlastname VARCHAR(150) NOT NULL,
userenabled bit(1) NOT NULL,
created_by VARCHAR(150) NOT NULL,
created_date VARCHAR(150) NOT NULL,
modified_by VARCHAR(150) NOT NULL,
modified_date VARCHAR(150) NOT NULL,
PRIMARY KEY (id),
UNIQUE (useremail, useruuid)) ENGINE=InnoDB;

create table app_role(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(150) NOT NULL,
PRIMARY KEY (id),
UNIQUE (name)) ENGINE=InnoDB;

CREATE TABLE app_user_role (
id BIGINT NOT NULL AUTO_INCREMENT,
userid BIGINT NOT NULL,
roleid INT NOT NULL,
PRIMARY KEY (id))ENGINE=InnoDB;

create table persistent_logins (
useremail varchar(64) not null,
series varchar(64) primary key,
token varchar(64) not null,
last_used timestamp not null)ENGINE=InnoDB;

create table app_db_content(
id INT NOT NULL AUTO_INCREMENT,
fileuuid VARCHAR(150),
filename VARCHAR(150) NOT NULL,
filetype VARCHAR(150) NOT NULL,
filesize VARCHAR(150) NOT NULL,
filedata longblob,
created_by VARCHAR(150) NOT NULL,
created_date VARCHAR(150) NOT NULL,
modified_by VARCHAR(150) NOT NULL,
modified_date VARCHAR(150) NOT NULL,
optlock INT,
PRIMARY KEY (id),
UNIQUE KEY(fileuuid),
UNIQUE (fileuuid)) ENGINE=InnoDB;

create table app_fs_content(
id INT NOT NULL AUTO_INCREMENT,
fileuuid VARCHAR(150),
filename VARCHAR(150) NOT NULL,
fileurl VARCHAR(150) NOT NULL,
filetype VARCHAR(150) NOT NULL,
filesize VARCHAR(150) NOT NULL,
created_by VARCHAR(150) NOT NULL,
created_date VARCHAR(150) NOT NULL,
modified_by VARCHAR(150) NOT NULL,
modified_date VARCHAR(150) NOT NULL,
optlock INT,
UNIQUE KEY(fileuuid),
PRIMARY KEY (id)) ENGINE=InnoDB;
 
create table aws_file_meta(
id INT NOT NULL AUTO_INCREMENT,
awsfilename VARCHAR(150),
awsfilepath VARCHAR(150) NOT NULL,
awsfileversion VARCHAR(150),
PRIMARY KEY (id)) ENGINE=InnoDB;

ALTER TABLE app_user_role ADD CONSTRAINT FK_AURUSERID FOREIGN KEY (userid) REFERENCES app_user (id);
ALTER TABLE app_user_role ADD CONSTRAINT FK_AURROLEID FOREIGN KEY (roleid) REFERENCES app_role (id); 


INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin@admin', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin@admin', 'admin@admin', TRUE, 'System', CURDATE(), 'System', CURDATE());   
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin1@admin1', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin1@admin1', 'admin1@admin1', TRUE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin2@admin2', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin2@admin2', 'admin2@admin2', TRUE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin3@admin3', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin3@admin3', 'admin3@admin3', FALSE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin4@admin4', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin4@admin4', 'admin4@admin4', TRUE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin5@admin5', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin5@admin5', 'admin5@admin5', FALSE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin6@admin6', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin6@admin6', 'admin6@admin6', TRUE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin7@admin7', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin7@admin7', 'admin7@admin7', FALSE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin8@admin8', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin8@admin8', 'admin8@admin8', TRUE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin9@admin9', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin9@admin9', 'admin9@admin9', FALSE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin10@admin10', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin10@admin10', 'admin10@admin10', TRUE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin11@admin11', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin11@admin11', 'admin11@admin11', FALSE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin12@admin12', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin12@admin12', 'admin12@admin12', TRUE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin13@admin13', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin13@admin13', 'admin13@admin13', FALSE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin14@admin14', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin14@admin14', 'admin14@admin14', FALSE, 'System', CURDATE(), 'System', CURDATE());
INSERT INTO `contentplusplus`.`app_user` (`useremail`, `useruuid`, `userpassword`, `userfirstname`, `userlastname`,`userenabled`,  `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('admin15@admin15', uuid(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin15@admin15', 'admin15@admin15', TRUE, 'System', CURDATE(), 'System', CURDATE());

INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('1', 'ADMIN');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('2', 'EDITOR');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('3', 'VIEWER');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('4', 'GUEST');

INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('2', '4');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('3', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('3', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('3', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('4', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('4', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('4', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('5', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('5', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('5', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('6', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('6', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('6', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('7', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('7', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('7', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('8', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('8', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('8', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('9', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('9', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('9', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('10', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('10', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('10', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('11', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('11', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('11', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('12', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('12', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('12', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('13', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('13', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('13', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('14', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('14', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('14', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('15', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('15', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('15', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('16', '3');