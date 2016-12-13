package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Lecture findLectureByTopicId(Long topicId);
}
