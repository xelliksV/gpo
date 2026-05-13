--liquibase formatted sql

--changeset gpo:002-create-academic-disciplines-table
CREATE TABLE academic_disciplines (
    uid BIGINT PRIMARY KEY,
    id VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(500),
    attendance_enabled BOOLEAN DEFAULT FALSE,
    viewer_id VARCHAR(255) REFERENCES viewers(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
