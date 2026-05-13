--liquibase formatted sql

--changeset gpo:006-create-homeworks-table
CREATE TABLE homeworks (
    uid BIGINT PRIMARY KEY,
    id VARCHAR(255) UNIQUE NOT NULL,
    typename VARCHAR(255),
    lesson_uid BIGINT REFERENCES lessons(uid),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
