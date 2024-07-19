-- Active: 1720747293453@@127.0.0.1@5432@ticketsdb
-- TABLES
CREATE TABLE IF NOT EXISTS ORGANIZATIONS (
    ID UUID PRIMARY KEY,
    NAME VARCHAR,
    CREATED_AT BIGINT,
    UPDATED_AT BIGINT,
    CREATED_BY UUID,
    UPDATED_BY UUID
);

CREATE TABLE IF NOT EXISTS ROLES(
    ID UUID PRIMARY KEY,
    NAME VARCHAR UNIQUE,
    CREATED_AT BIGINT,
    UPDATED_AT BIGINT,
    CREATED_BY UUID,
    UPDATED_BY UUID
);

CREATE TABLE IF NOT EXISTS USERS(
    ID UUID PRIMARY KEY,
    NAME VARCHAR,
    EMAIL VARCHAR UNIQUE NOT NULL,
    PASSWORD VARCHAR,
    ORGANIZATION_ID UUID NOT NULL REFERENCES ORGANIZATIONS(ID),
    ROLE_ID UUID NOT NULL REFERENCES ROLES(ID),
    VERIFIED_AT TIMESTAMP,
    CREATED_AT BIGINT,
    UPDATED_AT BIGINT,
    CREATED_BY UUID,
    UPDATED_BY UUID
);

CREATE TABLE IF NOT EXISTS TICKETS(
    ID UUID PRIMARY KEY,
    TITLE VARCHAR,
    DESCRIPTION VARCHAR,
    PRIORITY INTEGER,
    STATUS VARCHAR,
    REPORT_TO_USER_ID UUID NOT NULL REFERENCES USERS(ID),
    ASSIGNED_TO_USER_ID UUID,
    PROJECT_ID UUID,
    CREATED_AT BIGINT,
    UPDATED_AT BIGINT,
    CREATED_BY UUID,
    UPDATED_BY UUID
);

-- INSERTS
INSERT INTO ORGANIZATIONS(ID, NAME, CREATED_AT, UPDATED_AT, CREATED_BY, UPDATED_BY)
VALUES
    (gen_random_uuid(),
     'Netflix',
     extract(epoch from now() at time zone 'utc'),
     extract(epoch from now() at time zone 'utc'),
     null,
     null);

SELECT * FROM ORGANIZATIONS;

INSERT INTO ROLES(ID, NAME, CREATED_AT, UPDATED_AT, CREATED_BY, UPDATED_BY)
VALUES(
       gen_random_uuid(),
       'ADMIN',
       extract(epoch from now() at time zone 'utc'),
       extract(epoch from now() at time zone 'utc'),
       null,
       null
  );

SELECT * FROM ROLES;

/*
-- Copiar el Id de la Orgnization de lo siguiente instruccion
SELECT * FROM ORGANIZATIONS;

-- Copiar el Id del Role de lo siguiente instruccion
SELECT * FROM ROLES;

-- Los 2 Ids (Organization, Role) copiados debe ser agregado en la siguiente instruccion Insert en la tabla Users
*/
SELECT * FROM USERS;
-- ALTER TABLE USERS ADD VERIFIED_AT TIMESTAMP;
-- DELETE FROM USERS;

INSERT INTO USERS(ID, NAME, EMAIL, PASSWORD, ORGANIZATION_ID, ROLE_ID, CREATED_AT, UPDATED_AT, CREATED_BY, UPDATED_BY)
VALUES(
   gen_random_uuid(),
   'Reinaldo Acacio',
   'reyacacio@gmail.com',
   '123456',
   'ad15dc9b-e7aa-44f9-8552-356a2f2e0ba8',
   '7737e1b9-e20b-4f20-be83-70469e230abc',
   extract(epoch from now() at time zone 'utc'),
   extract(epoch from now() at time zone 'utc'),
   null, null
  );

  SELECT * FROM TICKETS;
  -- DROP TABLE IF EXISTS TICKETS;