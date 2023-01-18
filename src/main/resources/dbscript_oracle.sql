BEGIN
   FOR cur_rec IN (SELECT object_name, object_type
                     FROM user_objects
                    WHERE object_type IN
                             ('TABLE',
                              'VIEW',
                              'PACKAGE',
                              'PROCEDURE',
                              'FUNCTION',
                              'SEQUENCE',
                              'TYPE',
                              'SYNONYM',
                              'MATERIALIZED VIEW'
                             ))
   LOOP
      BEGIN
         IF cur_rec.object_type = 'TABLE'
         THEN
            EXECUTE IMMEDIATE    'DROP '
                              || cur_rec.object_type
                              || ' "'
                              || cur_rec.object_name
                              || '" CASCADE CONSTRAINTS';
         ELSE
            EXECUTE IMMEDIATE    'DROP '
                              || cur_rec.object_type
                              || ' "'
                              || cur_rec.object_name
                              || '"';
         END IF;
      EXCEPTION
         WHEN OTHERS
         THEN
            DBMS_OUTPUT.put_line (   'FAILED: DROP '
                                  || cur_rec.object_type
                                  || ' "'
                                  || cur_rec.object_name
                                  || '"'
                                 );
      END;
   END LOOP;
END;

BEGIN
   FOR cur_syn IN (SELECT synonym_name
                     FROM all_synonyms
                    WHERE table_owner = 'CONTENTPLUSPLUS')
   LOOP
      BEGIN
         EXECUTE IMMEDIATE 'drop public synonym ' || cur_syn.synonym_name ;
      EXCEPTION
         WHEN OTHERS
         THEN
            DBMS_OUTPUT.PUT_LINE ('Failed to drop the public synonym ' || cur_syn.synonym_name || '! ' || sqlerrm);
      END;
   END LOOP;
END;


--DROP USER CONTENTPLUSPLUS;

--CREATE user CONTENTPLUSPLUS identified by CONTENTPLUSPLUS;

--GRANT ALL PRIVILEGES TO CONTENTPLUSPLUS;

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




INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin@admin', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'Ajay', 'Kumar', 1, 'System', sysdate, 'System', sysdate);   
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin1@admin1', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin1@admin1', 'admin1@admin1', 1, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin2@admin2', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin2@admin2', 'admin2@admin2', 1, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin3@admin3', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin3@admin3', 'admin3@admin3', 0, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin4@admin4', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin4@admin4', 'admin4@admin4', 1, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin5@admin5', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin5@admin5', 'admin5@admin5', 0, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin6@admin6', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin6@admin6', 'admin6@admin6', 1, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin7@admin7', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin7@admin7', 'admin7@admin7', 0, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin8@admin8', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin8@admin8', 'admin8@admin8', 1, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin9@admin9', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin9@admin9', 'admin9@admin9', 0, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin10@admin10', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin10@admin10', 'admin10@admin10', 1, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin11@admin11', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin11@admin11', 'admin11@admin11', 0, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin12@admin12', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin12@admin12', 'admin12@admin12', 1, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin13@admin13', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin13@admin13', 'admin13@admin13', 0, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin14@admin14', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin14@admin14', 'admin14@admin14', 0, 'System', sysdate, 'System', sysdate);
INSERT INTO CONTENTPLUSPLUS.app_user (useremail, useruuid, userpassword, userfirstname, userlastname,userenabled,  created_by, created_date, modified_by, modified_date) VALUES ('admin15@admin15', SYS_GUID(), '$2a$10$kq8mcIw34jxCEHKEukfHs.PtvADISIhLUk29BtK0NZf8HK7fKdCqS', 'admin15@admin15', 'admin15@admin15', 1, 'System', sysdate, 'System', sysdate);


INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('ADMIN');
INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('GUEST');
INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('DOCWFDRAFTER');
INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('DOCWFREVIEWER');
INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('DOCWFAPPROVER');
INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('DOCWFFINALIZER');
INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('CASEWORKER');
INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('CASEEDITOR');
INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('CASEVIEWER');
INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('CASEREVIEWER');
INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('CASEDOCUPLOADER');
INSERT INTO CONTENTPLUSPLUS.app_role (name) VALUES ('CASEDOCREMOVER');

INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '11');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('1', '12');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('2', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('3', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('3', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('3', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('4', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('4', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('4', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('5', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('5', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('5', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('6', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('6', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('6', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('7', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('7', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('7', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('8', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('8', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('8', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('9', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('9', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('9', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('10', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('10', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('10', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('11', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('11', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('11', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('12', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('12', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('12', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('13', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('13', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('13', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('14', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('14', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('14', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('15', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('15', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('15', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_role (userid, roleid) VALUES ('16', '3');

INSERT INTO CONTENTPLUSPLUS.app_department (departmentname,  departmentheadname, departmentheademail,  created_by, created_date, modified_by, modified_date, userid) VALUES ('Department1', 'Department1HeadName1', 'Department1Head1@email.com', 'System', sysdate, 'System', sysdate,1);
INSERT INTO CONTENTPLUSPLUS.app_department (departmentname,  departmentheadname, departmentheademail,  created_by, created_date, modified_by, modified_date, userid) VALUES ('Department2', 'Department2HeadName1', 'Department2Head2@email.com', 'System', sysdate, 'System', sysdate,2);
INSERT INTO CONTENTPLUSPLUS.app_department (departmentname,  departmentheadname, departmentheademail,  created_by, created_date, modified_by, modified_date, userid) VALUES ('Department3', 'Department3HeadName3', 'Department3Head3@email.com', 'System', sysdate, 'System', sysdate,3);
INSERT INTO CONTENTPLUSPLUS.app_department (departmentname,  departmentheadname, departmentheademail,  created_by, created_date, modified_by, modified_date, userid) VALUES ('Department4', 'Department4HeadName4', 'Department4Head4@email.com', 'System', sysdate, 'System', sysdate,4);
INSERT INTO CONTENTPLUSPLUS.app_department (departmentname,  departmentheadname, departmentheademail,  created_by, created_date, modified_by, modified_date, userid) VALUES ('Department5', 'Department5HeadName5', 'Department5Head5@email.com', 'System', sysdate, 'System', sysdate,5);
INSERT INTO CONTENTPLUSPLUS.app_department (departmentname,  departmentheadname, departmentheademail,  created_by, created_date, modified_by, modified_date, userid) VALUES ('Department6', 'Department6HeadName6', 'Department6Head1@email.com', 'System', sysdate, 'System', sysdate,6);
INSERT INTO CONTENTPLUSPLUS.app_department (departmentname,  departmentheadname, departmentheademail,  created_by, created_date, modified_by, modified_date, userid) VALUES ('Department7', 'Department7HeadName7', 'Department7Head7@email.com', 'System', sysdate, 'System', sysdate,7);
INSERT INTO CONTENTPLUSPLUS.app_department (departmentname,  departmentheadname, departmentheademail,  created_by, created_date, modified_by, modified_date, userid) VALUES ('Department8', 'Department8HeadName8', 'Department8Head8@email.com', 'System', sysdate, 'System', sysdate,8);
INSERT INTO CONTENTPLUSPLUS.app_department (departmentname,  departmentheadname, departmentheademail,  created_by, created_date, modified_by, modified_date, userid) VALUES ('Department9', 'Department9HeadName9', 'Department9Head9@email.com', 'System', sysdate, 'System', sysdate,9);
INSERT INTO CONTENTPLUSPLUS.app_department (departmentname,  departmentheadname, departmentheademail,  created_by, created_date, modified_by, modified_date, userid) VALUES ('Department10', 'Department10HeadName10', 'Department10Head10@email.com', 'System', sysdate, 'System', sysdate, 10);

INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('1', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('1', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('2', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('2', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('3', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('3', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('3', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('3', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('3', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('3', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('3', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('3', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('3', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('3', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('4', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('4', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('4', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('4', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('4', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('4', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('4', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('4', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('4', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('4', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('5', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('5', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('5', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('5', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('5', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('5', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('5', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('5', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('5', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('5', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('6', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('6', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('6', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('6', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('6', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('6', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('6', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('6', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('6', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('6', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('7', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('7', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('7', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('7', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('7', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('7', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('7', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('7', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('7', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('7', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('8', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('8', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('8', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('8', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('8', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('8', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('8', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('8', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('8', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('8', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('9', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('9', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('9', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('9', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('9', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('9', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('9', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('9', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('9', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('9', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('10', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('10', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('10', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('10', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('10', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('10', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('10', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('10', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('10', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('10', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('11', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('11', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('11', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('11', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('11', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('11', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('11', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('11', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('11', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('11', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('12', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('12', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('12', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('12', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('12', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('12', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('12', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('12', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('12', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('12', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('13', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('13', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('13', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('13', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('13', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('13', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('13', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('13', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('13', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('13', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('14', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('14', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('14', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('14', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('14', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('14', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('14', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('14', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('14', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('14', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('15', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('15', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('15', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('15', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('15', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('15', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('15', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('15', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('15', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('15', '10');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('16', '1');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('16', '2');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('16', '3');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('16', '4');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('16', '5');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('16', '6');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('16', '7');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('16', '8');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('16', '9');
INSERT INTO CONTENTPLUSPLUS.app_user_department (userid, departmentid) VALUES ('16', '10');

commit;