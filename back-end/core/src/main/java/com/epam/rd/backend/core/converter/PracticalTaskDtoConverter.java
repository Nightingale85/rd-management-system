package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.PracticalTask;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PracticalTaskDtoConverter {
    public PracticalTaskDto convert(PracticalTask task) {
        PracticalTaskDto dto = new PracticalTaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getName());
        dto.setDeadline(task.getDateEnd());
        dto.setDescription(task.getLinkToDescription());
        dto.setTopicId(getTopicId(task));
        dto.setFunctionalRequirements(task.getFunctionalRequirements());
        dto.setNonFunctionalRequirements(task.getNonFunctionalRequirements());
        dto.setScoreSystem(task.getScoreSystem());
        dto.setAdditionalMaterials(task.getAdditionalMaterials());
        dto.setGuide(task.getGuide());
        return dto;
    }

    private Long getTopicId(PracticalTask task) {
        Topic topic = task.getTopic();
        return topic != null ? topic.getId() : null;
    }

    public PracticalTask convert(PracticalTaskDto dto) {
        Objects.requireNonNull(dto, "Practical Task DTO cannot be NULL");
        PracticalTask task = new PracticalTask();
        task.setId(dto.getId());
        task.setName(dto.getTitle());
        task.setDateEnd(dto.getDeadline());
        task.setLinkToDescription(dto.getDescription());
        task.setFunctionalRequirements(dto.getFunctionalRequirements());
        task.setNonFunctionalRequirements(dto.getNonFunctionalRequirements());
        task.setScoreSystem(dto.getScoreSystem());
        task.setAdditionalMaterials(dto.getAdditionalMaterials());
        task.setGuide(dto.getGuide());
        return task;
    }

    public PracticalTask convert(PracticalTaskDtoCreate dtoCreate) {
        Objects.requireNonNull(dtoCreate, "Practical Task DTO cannot be NULL");
        PracticalTask task = new PracticalTask();
        task.setName(dtoCreate.getTitle());
        task.setDateEnd(dtoCreate.getDeadline());
        task.setLinkToDescription(dtoCreate.getDescription());
        task.setFunctionalRequirements(dtoCreate.getFunctionalRequirements());
        task.setNonFunctionalRequirements(dtoCreate.getNonFunctionalRequirements());
        task.setScoreSystem(dtoCreate.getScoreSystem());
        task.setAdditionalMaterials(dtoCreate.getAdditionalMaterials());
        task.setGuide(dtoCreate.getGuide());
        return task;
    }
}
