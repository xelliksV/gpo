package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "quiz_details")
@Data
public class QuizDetails {
    @Id
    private String id;

    private Integer version;

    @Column(name = "positive_grade_border", precision = 10, scale = 2)
    private BigDecimal positiveGradeBorder;

    @Column(name = "time_limit_unit")
    private String timeLimitUnit;

    @Column(name = "time_limit_amount")
    private Integer timeLimitAmount;

    @Column(name = "attempts_count_limit")
    private Integer attemptsCountLimit;

    @Column(name = "questions_count_limit")
    private Integer questionsCountLimit;

    @Column(name = "display_correct_answers")
    private Boolean displayCorrectAnswers;

    @Column(name = "retry_questions_behaviour")
    private String retryQuestionsBehaviour;

    @Column(name = "answers_index_label_kind")
    private String answersIndexLabelKind;

    @Column(name = "is_gradable")
    private Boolean isGradable;

    @Column(name = "is_treat_scores_as_grades")
    private Boolean isTreatScoresAsGrades;

    @Column(name = "is_do_not_display_grade")
    private Boolean isDoNotDisplayGrade;

    @Column(name = "is_random_questions_order")
    private Boolean isRandomQuestionsOrder;

    @Column(name = "is_random_answers_order")
    private Boolean isRandomAnswersOrder;

    @Column(name = "is_display_completed_results_to_student")
    private Boolean isDisplayCompletedResultsToStudent;

    @Column(name = "is_inline")
    private Boolean isInline;

    @Column(name = "is_do_not_display_first_slide")
    private Boolean isDoNotDisplayFirstSlide;

    @Column(name = "is_do_not_display_last_slide")
    private Boolean isDoNotDisplayLastSlide;

    @Column(name = "is_display_all_questions_together")
    private Boolean isDisplayAllQuestionsTogether;

    @Column(name = "is_answers_any_order_allowed")
    private Boolean isAnswersAnyOrderAllowed;

    @Column(name = "homework_id")
    private String homeworkId;
}
