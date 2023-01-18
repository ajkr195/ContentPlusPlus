create table CONTENTPLUSPLUS.app_user (
id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1) NOT NULL,
useremail VARCHAR(150) NOT NULL,
userpassword VARCHAR(150) NOT NULL,
useruuid VARCHAR(50) NOT NULL,
userfirstname VARCHAR(150) NOT NULL,
userlastname VARCHAR(150) NOT NULL,
userenabled NUMBER(1) DEFAULT 0 NOT NULL,
created_by VARCHAR(150) NOT NULL,
created_date TIMESTAMP NOT NULL,
modified_by VARCHAR(150) NOT NULL,
modified_date TIMESTAMP NOT NULL,
CONSTRAINT appuser_pk PRIMARY KEY (id), UNIQUE (useremail, useruuid));


create table CONTENTPLUSPLUS.app_role(
id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1) NOT NULL,
name VARCHAR(150) NOT NULL,
CONSTRAINT approle_pk PRIMARY KEY (id),UNIQUE (name));


CREATE TABLE CONTENTPLUSPLUS.app_department (
id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1) NOT NULL,
departmentuuid VARCHAR(150),
departmentheadname varchar(255) NOT NULL,
departmentheademail varchar(255) NOT NULL,
departmentname varchar(255) NOT NULL,
userid NUMBER NOT NULL,
created_by VARCHAR(150) NOT NULL,
created_date TIMESTAMP NOT NULL,
modified_by VARCHAR(150) NOT NULL,
modified_date TIMESTAMP NOT NULL,
CONSTRAINT appdepartment_pk PRIMARY KEY (id),UNIQUE (departmentname, departmentuuid));

CREATE TABLE CONTENTPLUSPLUS.app_user_department (
userid NUMBER NOT NULL,
departmentid NUMBER NOT NULL
);

ALTER TABLE CONTENTPLUSPLUS.app_user_department ADD CONSTRAINT FK_AUSERDEPTUSERID FOREIGN KEY (userid) REFERENCES app_user (id);
ALTER TABLE CONTENTPLUSPLUS.app_user_department ADD CONSTRAINT FK_AUSERDEPTDEPTID FOREIGN KEY (departmentid) REFERENCES app_department (id); 
ALTER TABLE CONTENTPLUSPLUS.app_department ADD CONSTRAINT FK_AUSERUSERID FOREIGN KEY (userid) REFERENCES app_user (id);

CREATE TABLE CONTENTPLUSPLUS.app_user_role (
id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1) NOT NULL,
userid NUMBER NOT NULL,
roleid NUMBER NOT NULL,
CONSTRAINT appuserrole_pk PRIMARY KEY (id));

ALTER TABLE CONTENTPLUSPLUS.app_user_role ADD CONSTRAINT FK_AURUSERID FOREIGN KEY (userid) REFERENCES app_user (id);
ALTER TABLE CONTENTPLUSPLUS.app_user_role ADD CONSTRAINT FK_AURROLEID FOREIGN KEY (roleid) REFERENCES app_role (id); 

create table CONTENTPLUSPLUS.app_db_content(
id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1) NOT NULL,
fileuuid VARCHAR(150),
filename VARCHAR(150) NOT NULL,
filetype VARCHAR(150) NOT NULL,
filesize VARCHAR(150) NOT NULL,
filedata BLOB,
created_by VARCHAR(150) NOT NULL,
created_date TIMESTAMP NOT NULL,
modified_by VARCHAR(150) NOT NULL,
modified_date TIMESTAMP NOT NULL,
optlock number,
CONSTRAINT appdbcontent_pk PRIMARY KEY (id),UNIQUE (fileuuid));

create table CONTENTPLUSPLUS.app_workflow_document(
id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1) NOT NULL,
fileuuid VARCHAR(150),
filename VARCHAR(150) NOT NULL,
filetype VARCHAR(150) NOT NULL,
filesize VARCHAR(150) NOT NULL,
filedata BLOB,
created_by VARCHAR(150) NOT NULL,
created_date TIMESTAMP NOT NULL,
modified_by VARCHAR(150) NOT NULL,
modified_date TIMESTAMP NOT NULL,
optlock INT,
workflowstatus VARCHAR(150) NOT NULL,
CONSTRAINT appwfdocument_pk PRIMARY KEY (id),UNIQUE (fileuuid));

create table CONTENTPLUSPLUS.app_fs_content(
id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1) NOT NULL,
fileuuid VARCHAR(150),
filename VARCHAR(150) NOT NULL,
fileurl VARCHAR(150) NOT NULL,
filetype VARCHAR(150) NOT NULL,
filesize VARCHAR(150) NOT NULL,
created_by VARCHAR(150) NOT NULL,
created_date TIMESTAMP NOT NULL,
modified_by VARCHAR(150) NOT NULL,
modified_date TIMESTAMP NOT NULL,
optlock NUMBER,
CONSTRAINT appfscontent_pk PRIMARY KEY (id),UNIQUE (fileuuid));
 
create table CONTENTPLUSPLUS.aws_file_meta(
id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1) NOT NULL,
awsfilename VARCHAR(150),
awsfilepath VARCHAR(150) NOT NULL,
awsfileversion VARCHAR(150),
CONSTRAINT awsfilemeta_pk PRIMARY KEY (id));