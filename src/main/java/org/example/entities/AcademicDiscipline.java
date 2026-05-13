package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "academic_disciplines")
@Data
public class AcademicDiscipline {
    @Id
    private Long uid;

    @Column(unique = true, nullable = false)
    private String id;

    private String name;

    @Column(name = "attendance_enabled")
    private Boolean attendanceEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewer_id")
    private Viewer viewer;

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
