package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "homeworks")
@Data
public class HomeWork {
    @Id
    private Long uid;

    @Column(unique = true, nullable = false)
    private String id;

    private String typename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_uid")
    private Lesson lesson;

    @OneToMany(mappedBy = "homeWork", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

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
