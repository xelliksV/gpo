package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "viewers")
@Data
public class Viewer {
    @Id
    private String id;

    @OneToMany(mappedBy = "viewer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AcademicDiscipline> academicDisciplines;

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
