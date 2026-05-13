--liquibase formatted sql

--changeset gpo:010-create-question-answers-table
CREATE TABLE question_answers (
    uid VARCHAR(255) PRIMARY KEY,
    answer_selected_ids TEXT,
    answer_match_pairs TEXT,
    answer_writes TEXT,
    answer_fill_blanks TEXT,
    answer_text_mistakes TEXT,
    answer_text_mistake_state TEXT,
    answer_distribute_by_groups TEXT,
    answer_words_sequence TEXT,
    answer_select_area TEXT,
    answer_drag_and_drop_to_area TEXT,
    is_correct BOOLEAN DEFAULT FALSE,
    is_picked_from_previous_results BOOLEAN DEFAULT FALSE,
    scores DECIMAL(10,2),
    rewards TEXT,
    quiz_id VARCHAR(255)
);
