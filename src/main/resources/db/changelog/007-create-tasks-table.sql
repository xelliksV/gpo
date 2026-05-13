--liquibase formatted sql

--changeset gpo:007-create-tasks-table
CREATE TABLE tasks (
    uid BIGINT PRIMARY KEY,
    process_note TEXT,
    question TEXT,
    question_json TEXT,
    question_html TEXT,
    scores_kind VARCHAR(255),
    max_scores_amount DECIMAL(10,2),
    per_question_scores_amount DECIMAL(10,2),
    partial_answers BOOLEAN DEFAULT FALSE,
    extra_rewards TEXT,
    kind VARCHAR(255),
    answer_parts TEXT,
    homework_uid BIGINT REFERENCES homeworks(uid),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
