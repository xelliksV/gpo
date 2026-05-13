--liquibase formatted sql

--changeset gpo:003-create-learning-groups-table
CREATE TABLE learning_groups (
    id VARCHAR(255) PRIMARY KEY,
    typename VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
