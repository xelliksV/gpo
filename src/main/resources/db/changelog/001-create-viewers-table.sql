--liquibase formatted sql

--changeset gpo:001-create-viewers-table
CREATE TABLE viewers (
    id VARCHAR(255) PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
