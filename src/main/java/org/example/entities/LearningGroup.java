package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "learning_groups")
@Data
public class LearningGroup {
    @Id
    private String id;

    private String typename;

    @OneToMany(mappedBy = "learningGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "learningGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "learningGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttendanceRecord> attendanceRecords;

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
