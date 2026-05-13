package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_records")
@Data
public class AttendanceRecord {
    @Id
    private String uid;

    private String id;

    @Column(name = "learning_discipline_id")
    private String learningDisciplineId;

    @Column(name = "learning_discipline_lesson_id")
    private String learningDisciplineLessonId;

    @Column(name = "status_id")
    private String statusId;

    @Column(name = "grade_lesson", precision = 10, scale = 2)
    private BigDecimal gradeLesson;

    @Column(name = "grade_homeworks_assignment", precision = 10, scale = 2)
    private BigDecimal gradeHomeworksAssignment;

    @Column(name = "grade_homeworks_examination", precision = 10, scale = 2)
    private BigDecimal gradeHomeworksExamination;

    @Column(name = "grade_homeworks_independent_work", precision = 10, scale = 2)
    private BigDecimal gradeHomeworksIndependentWork;

    @Column(name = "is_abonnement_used")
    private Boolean isAbonnementUsed;

    @Column(name = "comment_teacher", columnDefinition = "TEXT")
    private String commentTeacher;

    @Column(name = "comment_group_director", columnDefinition = "TEXT")
    private String commentGroupDirector;

    @Column(name = "comment_tutor", columnDefinition = "TEXT")
    private String commentTutor;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "status_icon_kind")
    private String statusIconKind;

    @Column(name = "status_color")
    private String statusColor;

    @Column(name = "status_is_stats_table_column_displayed")
    private Boolean statusIsStatsTableColumnDisplayed;

    @Column(name = "status_state")
    private String statusState;

    @Column(name = "status_kind")
    private String statusKind;

    @Column(name = "status_order_index")
    private Integer statusOrderIndex;

    @Column(name = "status_deleted_at")
    private LocalDateTime statusDeletedAt;

    @Column(name = "status_org_id")
    private String statusOrgId;

    @Column(name = "status_uid")
    private String statusUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_group_id")
    private LearningGroup learningGroup;

    @Column(name = "created_at_entity")
    private LocalDateTime createdAtEntity;

    @Column(name = "updated_at_entity")
    private LocalDateTime updatedAtEntity;

    @PrePersist
    protected void onCreate() {
        createdAtEntity = LocalDateTime.now();
        updatedAtEntity = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAtEntity = LocalDateTime.now();
    }
}
