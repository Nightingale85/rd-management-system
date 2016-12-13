package com.epam.rd.frontend.service;

import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;
import org.springframework.security.access.annotation.Secured;

public interface PracticalTaskService {
    PracticalTaskDto getPracticalTaskDtoByTopicId(Long topicId);

    PracticalTaskDto getPracticalTaskDtoById(Long id);

    @Secured("ROLE_LECTURER")
    Long addPracticalTask(PracticalTaskDtoCreate dtoCreate);

    @Secured("ROLE_LECTURER")
    PracticalTaskDto updatePracticalTask(PracticalTaskDto taskDto);

    @Secured("ROLE_LECTURER")
    void deletePracticalTaskById(Long id);
}
