package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.model.PracticalTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticalTaskRepository extends JpaRepository<PracticalTask, Long> {
    PracticalTask findPracticalTaskByTopicId(Long topicId);
}
