package org.example.repositories;

import org.example.entities.HomeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeWorkRepository extends JpaRepository<HomeWork, Long> {
}
