package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "teachers")
@Data
public class Teacher {
    @Id
    private String id;

    @Column(name = "teacher_id")
    private String teacherId;

    private String roles;

    @Column(name = "is_disabled")
    private Boolean isDisabled;

    @Column(name = "is_interactive_lessons")
    private Boolean isInteractiveLessons;

    @Column(name = "inherited_from_learning_configuration_id")
    private String inheritedFromLearningConfigurationId;

    private Long uid;

    @Column(name = "human_first_name")
    private String humanFirstName;

    @Column(name = "human_middle_name")
    private String humanMiddleName;

    @Column(name = "human_last_name")
    private String humanLastName;

    @Column(name = "human_id")
    private String humanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_group_id")
    private LearningGroup learningGroup;

    @Column(name = "selection_behaviour")
    private String selectionBehaviour;

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
