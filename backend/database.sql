CREATE DATABASE technical_test_bva;

USE technical_test_bva;

CREATE TABLE users
(
    username         VARCHAR(100) NOT NULL,
    password         VARCHAR(100) NOT NULL,
    token            VARCHAR(255),
    token_expired_at BIGINT,
    PRIMARY KEY (username),
    UNIQUE (token)
) ENGINE InnoDB;

CREATE TABLE members (
    id               VARCHAR(100) NOT NULL,
    username   		 VARCHAR(100) NOT NULL,
    name             VARCHAR(100) NOT NULL,
    position         VARCHAR(100) NOT NULL,
    superior         VARCHAR(100),
    picture_url      VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY fk_users_members (username) REFERENCES users (username)
) ENGINE InnoDB;

SELECT * FROM users;

DESC users;

SELECT * FROM members;

DESC members;