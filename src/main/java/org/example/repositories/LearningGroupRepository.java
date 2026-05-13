package org.example.repositories;

import org.example.entities.LearningGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningGroupRepository extends JpaRepository<LearningGroup, String> {
}
