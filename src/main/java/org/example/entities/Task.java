package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    private Long uid;

    @Column(name = "process_note")
    private String processNote;

    private String question;

    @Column(name = "question_json", columnDefinition = "TEXT")
    private String questionJson;

    @Column(name = "question_html", columnDefinition = "TEXT")
    private String questionHtml;

    @Column(name = "scores_kind")
    private String scoresKind;

    @Column(name = "max_scores_amount", precision = 10, scale = 2)
    private BigDecimal maxScoresAmount;

    @Column(name = "per_question_scores_amount", precision = 10, scale = 2)
    private BigDecimal perQuestionScoresAmount;

    @Column(name = "partial_answers")
    private Boolean partialAnswers;

    @Column(name = "extra_rewards", columnDefinition = "TEXT")
    private String extraRewards;

    private String kind;

    @Column(name = "answer_parts", columnDefinition = "TEXT")
    private String answerParts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_uid")
    private HomeWork homeWork;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
