package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "question_answers")
@Data
public class QuestionAnswer {
    @Id
    private String uid;

    @Column(name = "answer_selected_ids", columnDefinition = "TEXT")
    private String answerSelectedIds;

    @Column(name = "answer_match_pairs", columnDefinition = "TEXT")
    private String answerMatchPairs;

    @Column(name = "answer_writes", columnDefinition = "TEXT")
    private String answerWrites;

    @Column(name = "answer_fill_blanks", columnDefinition = "TEXT")
    private String answerFillBlanks;

    @Column(name = "answer_text_mistakes", columnDefinition = "TEXT")
    private String answerTextMistakes;

    @Column(name = "answer_text_mistake_state", columnDefinition = "TEXT")
    private String answerTextMistakeState;

    @Column(name = "answer_distribute_by_groups", columnDefinition = "TEXT")
    private String answerDistributeByGroups;

    @Column(name = "answer_words_sequence", columnDefinition = "TEXT")
    private String answerWordsSequence;

    @Column(name = "answer_select_area", columnDefinition = "TEXT")
    private String answerSelectArea;

    @Column(name = "answer_drag_and_drop_to_area", columnDefinition = "TEXT")
    private String answerDragAndDropToArea;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "is_picked_from_previous_results")
    private Boolean isPickedFromPreviousResults;

    @Column(name = "scores", precision = 10, scale = 2)
    private BigDecimal scores;

    @Column(name = "rewards", columnDefinition = "TEXT")
    private String rewards;

    @Column(name = "quiz_id")
    private String quizId;
}
