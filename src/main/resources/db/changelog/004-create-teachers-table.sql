--liquibase formatted sql

--changeset gpo:004-create-teachers-table
CREATE TABLE teachers (
    id VARCHAR(255) PRIMARY KEY,
    teacher_id VARCHAR(255),
    roles TEXT,
    is_disabled BOOLEAN DEFAULT FALSE,
    is_interactive_lessons BOOLEAN DEFAULT FALSE,
    inherited_from_learning_configuration_id VARCHAR(255),
    uid BIGINT,
    human_first_name VARCHAR(255),
    human_middle_name VARCHAR(255),
    human_last_name VARCHAR(255),
    human_id VARCHAR(255),
    learning_group_id VARCHAR(255) REFERENCES learning_groups(id),
    selection_behaviour VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
