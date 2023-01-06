BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE "contentplusplus".app_user';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;

create table "contentplusplus".app_user (
id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1) NOT NULL,
useremail VARCHAR(150) NOT NULL,
userpassword VARCHAR(150) NOT NULL,
useruuid VARCHAR(50) NOT NULL,
userfirstname VARCHAR(150) NOT NULL,
userlastname VARCHAR(150) NOT NULL,
userenabled NUMBER(1) DEFAULT 0 NOT NULL,
created_by VARCHAR(150) NOT NULL,
created_date VARCHAR(150) NOT NULL,
modified_by VARCHAR(150) NOT NULL,
modified_date VARCHAR(150) NOT NULL,
UNIQUE  ( useremail, useruuid));

