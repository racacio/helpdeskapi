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

INSERT INTO USERS(ID, NAME, EMAIL, PASSWORD, ORGANIZATION_ID, ROLE_ID, CREATED_AT, UPDATED_AT, CREATED_BY, UPDATED_BY)
VALUES(
   gen_random_uuid(),
   'Reinaldo Acacio',
   'reyacacio@gmail.com',
   '123456',
   '2e86f429-4dc9-4dee-8bd3-44bb0ddc65ff',
   'c53c8906-3e41-45a9-a568-14f534fd042a',
   extract(epoch from now() at time zone 'utc'),
   extract(epoch from now() at time zone 'utc'),
   null, null
  );