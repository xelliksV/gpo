package org.example.repositories;

import org.example.entities.QuizDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDetailsRepository extends JpaRepository<QuizDetails, String> {
}
