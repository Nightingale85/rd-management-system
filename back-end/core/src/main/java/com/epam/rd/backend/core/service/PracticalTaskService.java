package com.epam.rd.backend.core.service;

import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;

import java.util.List;

public interface PracticalTaskService {
    PracticalTaskDto createPracticalTask(PracticalTaskDtoCreate dtoCreate);

    PracticalTaskDto updatePracticalTask(PracticalTaskDto dto);

    List<PracticalTaskDto> getAllPracticalTask();

    PracticalTaskDto getPracticalTaskById(Long id);

    void deletePracticalTaskById(Long id);

    PracticalTaskDto getPracticalTaskByTopicId(Long topicId);
}
