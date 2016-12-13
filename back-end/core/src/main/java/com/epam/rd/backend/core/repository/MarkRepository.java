package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findMarksByMenteeEmail(String email);

    List<Mark> findMarksByPracticalTaskIdAndEstimatorEmail(Long practicalTaskId, String estimatorEmail);
}
