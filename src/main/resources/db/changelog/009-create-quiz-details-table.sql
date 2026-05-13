--liquibase formatted sql

--changeset gpo:009-create-quiz-details-table
CREATE TABLE quiz_details (
    id VARCHAR(255) PRIMARY KEY,
    version INT,
    positive_grade_border DECIMAL(10,2),
    time_limit_unit VARCHAR(50),
    time_limit_amount INT,
    attempts_count_limit INT,
    questions_count_limit INT,
    display_correct_answers BOOLEAN DEFAULT FALSE,
    retry_questions_behaviour VARCHAR(255),
    answers_index_label_kind VARCHAR(255),
    is_gradable BOOLEAN DEFAULT FALSE,
    is_treat_scores_as_grades BOOLEAN DEFAULT FALSE,
    is_do_not_display_grade BOOLEAN DEFAULT FALSE,
    is_random_questions_order BOOLEAN DEFAULT FALSE,
    is_random_answers_order BOOLEAN DEFAULT FALSE,
    is_display_completed_results_to_student BOOLEAN DEFAULT FALSE,
    is_inline BOOLEAN DEFAULT FALSE,
    is_do_not_display_first_slide BOOLEAN DEFAULT FALSE,
    is_do_not_display_last_slide BOOLEAN DEFAULT FALSE,
    is_display_all_questions_together BOOLEAN DEFAULT FALSE,
    is_answers_any_order_allowed BOOLEAN DEFAULT FALSE,
    homework_id VARCHAR(255)
);
