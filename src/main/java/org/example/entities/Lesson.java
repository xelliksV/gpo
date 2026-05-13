package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "lessons")
@Data
public class Lesson {
    @Id
    private Long uid;

    @Column(unique = true, nullable = false)
    private String id;

    private String typename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_group_id")
    private LearningGroup learningGroup;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HomeWork> homeWorks;

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
