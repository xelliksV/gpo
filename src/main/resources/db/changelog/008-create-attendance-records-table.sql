--liquibase formatted sql

--changeset gpo:008-create-attendance-records-table
CREATE TABLE attendance_records (
    uid VARCHAR(255) PRIMARY KEY,
    id VARCHAR(255),
    learning_discipline_id VARCHAR(255),
    learning_discipline_lesson_id VARCHAR(255),
    status_id VARCHAR(255),
    grade_lesson DECIMAL(10,2),
    grade_homeworks_assignment DECIMAL(10,2),
    grade_homeworks_examination DECIMAL(10,2),
    grade_homeworks_independent_work DECIMAL(10,2),
    is_abonnement_used BOOLEAN DEFAULT FALSE,
    comment_teacher TEXT,
    comment_group_director TEXT,
    comment_tutor TEXT,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP,
    status_name VARCHAR(255),
    status_icon_kind VARCHAR(255),
    status_color VARCHAR(255),
    status_is_stats_table_column_displayed BOOLEAN DEFAULT FALSE,
    status_state VARCHAR(255),
    status_kind VARCHAR(255),
    status_order_index INT,
    status_deleted_at TIMESTAMP,
    status_org_id VARCHAR(255),
    status_uid VARCHAR(255),
    learning_group_id VARCHAR(255) REFERENCES learning_groups(id)
);
