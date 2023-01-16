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

CREATE TABLE app_department (
  id BIGINT NOT NULL AUTO_INCREMENT,
  departmentuuid VARCHAR(150),
  departmentheadname varchar(255) NOT NULL,
  departmentheademail varchar(255) NOT NULL,
  departmentname varchar(255) NOT NULL,
  userid bigint NOT NULL,
  created_by VARCHAR(150) NOT NULL,
  created_date VARCHAR(150) NOT NULL,
  modified_by VARCHAR(150) NOT NULL,
  modified_date VARCHAR(150) NOT NULL,
  PRIMARY KEY (id)) ENGINE=InnoDB;
  
CREATE TABLE app_user_department (
  userid BIGINT NOT NULL,
  departmentid BIGINT NOT NULL
) ENGINE=InnoDB;
ALTER TABLE app_user_department ADD CONSTRAINT FK_AUSERDEPTUSERID FOREIGN KEY (userid) REFERENCES app_user (id);
ALTER TABLE app_user_department ADD CONSTRAINT FK_AUSERDEPTDEPTID FOREIGN KEY (departmentid) REFERENCES app_department (id); 
ALTER TABLE app_department ADD CONSTRAINT FK_AUSERUSERID FOREIGN KEY (userid) REFERENCES app_user (id);

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

create table app_workflow_document(
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
workflowstatus VARCHAR(150) NOT NULL,
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
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('2', 'GUEST');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('3', 'DOCWFDRAFTER');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('4', 'DOCWFREVIEWER');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('5', 'DOCWFAPPROVER');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('6', 'DOCWFFINALIZER');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('7', 'CASEWORKER');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('8', 'CASEEDITOR');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('9', 'CASEVIEWER');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('10', 'CASEREVIEWER');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('11', 'CASEDOCUPLOADER');
INSERT INTO `contentplusplus`.`app_role` (`id`, `name`) VALUES ('12', 'CASEDOCREMOVER');

INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '1');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '2');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '3');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '4');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '5');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '6');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '7');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '8');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '9');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '10');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '11');
INSERT INTO `contentplusplus`.`app_user_role` (`userid`, `roleid`) VALUES ('1', '12');
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

INSERT INTO app_department (departmentname,  departmentheadname, departmentheademail,  `created_by`, `created_date`, `modified_by`, `modified_date`, userid) VALUES ('Department1', 'Department1HeadName1', 'Department1Head1@email.com', 'System', CURDATE(), 'System', CURDATE(),1);
INSERT INTO app_department (departmentname,  departmentheadname, departmentheademail,  `created_by`, `created_date`, `modified_by`, `modified_date`, userid) VALUES ('Department2', 'Department2HeadName1', 'Department2Head2@email.com', 'System', CURDATE(), 'System', CURDATE(),2);
INSERT INTO app_department (departmentname,  departmentheadname, departmentheademail,  `created_by`, `created_date`, `modified_by`, `modified_date`, userid) VALUES ('Department3', 'Department3HeadName3', 'Department3Head3@email.com', 'System', CURDATE(), 'System', CURDATE(),3);
INSERT INTO app_department (departmentname,  departmentheadname, departmentheademail,  `created_by`, `created_date`, `modified_by`, `modified_date`, userid) VALUES ('Department4', 'Department4HeadName4', 'Department4Head4@email.com', 'System', CURDATE(), 'System', CURDATE(),4);
INSERT INTO app_department (departmentname,  departmentheadname, departmentheademail,  `created_by`, `created_date`, `modified_by`, `modified_date`, userid) VALUES ('Department5', 'Department5HeadName5', 'Department5Head5@email.com', 'System', CURDATE(), 'System', CURDATE(),5);
INSERT INTO app_department (departmentname,  departmentheadname, departmentheademail,  `created_by`, `created_date`, `modified_by`, `modified_date`, userid) VALUES ('Department6', 'Department6HeadName6', 'Department6Head1@email.com', 'System', CURDATE(), 'System', CURDATE(),6);
INSERT INTO app_department (departmentname,  departmentheadname, departmentheademail,  `created_by`, `created_date`, `modified_by`, `modified_date`, userid) VALUES ('Department7', 'Department7HeadName7', 'Department7Head7@email.com', 'System', CURDATE(), 'System', CURDATE(),7);
INSERT INTO app_department (departmentname,  departmentheadname, departmentheademail,  `created_by`, `created_date`, `modified_by`, `modified_date`, userid) VALUES ('Department8', 'Department8HeadName8', 'Department8Head8@email.com', 'System', CURDATE(), 'System', CURDATE(),8);
INSERT INTO app_department (departmentname,  departmentheadname, departmentheademail,  `created_by`, `created_date`, `modified_by`, `modified_date`, userid) VALUES ('Department9', 'Department9HeadName9', 'Department9Head9@email.com', 'System', CURDATE(), 'System', CURDATE(),9);
INSERT INTO app_department (departmentname,  departmentheadname, departmentheademail,  `created_by`, `created_date`, `modified_by`, `modified_date`, userid) VALUES ('Department10', 'Department10HeadName10', 'Department10Head10@email.com', 'System', CURDATE(), 'System', CURDATE(), 10);

INSERT INTO app_user_department (userid, departmentid) VALUES ('1', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('1', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('2', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('2', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('3', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('3', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('3', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('3', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('3', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('3', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('3', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('3', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('3', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('3', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('4', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('4', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('4', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('4', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('4', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('4', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('4', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('4', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('4', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('4', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('5', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('5', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('5', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('5', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('5', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('5', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('5', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('5', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('5', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('5', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('6', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('6', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('6', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('6', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('6', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('6', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('6', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('6', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('6', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('6', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('7', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('7', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('7', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('7', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('7', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('7', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('7', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('7', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('7', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('7', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('8', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('8', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('8', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('8', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('8', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('8', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('8', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('8', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('8', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('8', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('9', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('9', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('9', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('9', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('9', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('9', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('9', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('9', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('9', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('9', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('10', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('10', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('10', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('10', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('10', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('10', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('10', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('10', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('10', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('10', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('11', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('11', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('11', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('11', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('11', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('11', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('11', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('11', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('11', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('11', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('12', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('12', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('12', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('12', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('12', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('12', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('12', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('12', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('12', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('12', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('13', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('13', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('13', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('13', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('13', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('13', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('13', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('13', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('13', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('13', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('14', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('14', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('14', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('14', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('14', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('14', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('14', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('14', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('14', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('14', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('15', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('15', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('15', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('15', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('15', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('15', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('15', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('15', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('15', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('15', '10');
INSERT INTO app_user_department (userid, departmentid) VALUES ('16', '1');
INSERT INTO app_user_department (userid, departmentid) VALUES ('16', '2');
INSERT INTO app_user_department (userid, departmentid) VALUES ('16', '3');
INSERT INTO app_user_department (userid, departmentid) VALUES ('16', '4');
INSERT INTO app_user_department (userid, departmentid) VALUES ('16', '5');
INSERT INTO app_user_department (userid, departmentid) VALUES ('16', '6');
INSERT INTO app_user_department (userid, departmentid) VALUES ('16', '7');
INSERT INTO app_user_department (userid, departmentid) VALUES ('16', '8');
INSERT INTO app_user_department (userid, departmentid) VALUES ('16', '9');
INSERT INTO app_user_department (userid, departmentid) VALUES ('16', '10');






