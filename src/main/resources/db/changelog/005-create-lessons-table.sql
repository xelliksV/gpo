--liquibase formatted sql

--changeset gpo:005-create-lessons-table
CREATE TABLE lessons (
    uid BIGINT PRIMARY KEY,
    id VARCHAR(255) UNIQUE NOT NULL,
    typename VARCHAR(255),
    learning_group_id VARCHAR(255) REFERENCES learning_groups(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
