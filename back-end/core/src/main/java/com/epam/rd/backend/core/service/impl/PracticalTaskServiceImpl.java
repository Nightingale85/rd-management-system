package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.PracticalTaskDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.exception.EntityIsAlreadyExistException;
import com.epam.rd.backend.core.model.PracticalTask;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.backend.core.repository.PracticalTaskRepository;
import com.epam.rd.backend.core.repository.TopicRepository;
import com.epam.rd.backend.core.service.PracticalTaskService;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Service
public class PracticalTaskServiceImpl implements PracticalTaskService {
    private PracticalTaskRepository taskRepository;
    private TopicRepository topicRepository;
    private PracticalTaskDtoConverter converter;

    @Autowired
    public PracticalTaskServiceImpl(PracticalTaskRepository taskRepository,
                                    TopicRepository topicRepository,
                                    PracticalTaskDtoConverter converter) {
        this.taskRepository = taskRepository;
        this.topicRepository = topicRepository;
        this.converter = converter;
    }

    @Override
    public PracticalTaskDto createPracticalTask(PracticalTaskDtoCreate dtoCreate) {
        PracticalTask task = converter.convert(dtoCreate);
        Topic topic = getTopicById(dtoCreate.getTopicId());
        if (!isNull(topic.getPracticalTask())) {
            throw new EntityIsAlreadyExistException(
                    format("Topic with ID=%d already contains practical task with ID=%d",
                            topic.getId(),
                            topic.getPracticalTask().getId()));
        }
        topic.setPracticalTask(task);
        return converter.convert(taskRepository.save(task));
    }

    private Topic getTopicById(Long topicId) {
        Topic topic = topicRepository.findOne(topicId);
        if (isNull(topic)) {
            throw new EntityDoesNotExistException(
                    format("Topic with ID=%d does not exist", topicId));
        }
        return topic;
    }

    @Override
    public PracticalTaskDto updatePracticalTask(PracticalTaskDto dto) {
        PracticalTask task = converter.convert(dto);
        if (isTaskAbsent(task.getId())) {
            throw new EntityDoesNotExistException(
                    format("Practical task with ID=%s does not exist", task.getId()));
        }
        Topic topic = getTopicById(dto.getTopicId());
        if (isNull(topic.getPracticalTask())) {
            throw new EntityIsAlreadyExistException(
                    format("Topic with ID=%d doesn't contains practical task with ID=%d",
                            topic.getId(),
                            dto.getId()));
        }
        topic.setPracticalTask(task);
        return converter.convert(taskRepository.save(task));
    }

    private boolean isTaskAbsent(Long id) {
        return isNull(id) || taskRepository.findOne(id) == null;
    }

    @Override
    @Transactional
    public List<PracticalTaskDto> getAllPracticalTask() {
        final List<PracticalTask> tasks = taskRepository.findAll();
        return tasks.stream()
                    .map(task -> converter.convert(task))
                    .collect(toList());
    }

    @Override
    @Transactional
    public PracticalTaskDto getPracticalTaskById(Long id) {
        PracticalTask task = taskRepository.findOne(id);
        if (task == null) {
            throw new EntityDoesNotExistException(format("Cannot find practical task by ID=%s", id));
        }
        return converter.convert(task);
    }

    @Override
    public void deletePracticalTaskById(Long id) {
        if (isTaskAbsent(id)) {
            throw new EntityDoesNotExistException(format("Cannot find practical task by ID=%s", id));
        }
        taskRepository.delete(id);
    }

    @Override
    @Transactional
    public PracticalTaskDto getPracticalTaskByTopicId(Long topicId) {
        PracticalTask task = taskRepository.findPracticalTaskByTopicId(topicId);
        if (isNull(task)) {
            throw new EntityDoesNotExistException(format("Cannot find practical task by Topic ID=%s",
                    topicId));
        }
        return converter.convert(task);
    }

}
