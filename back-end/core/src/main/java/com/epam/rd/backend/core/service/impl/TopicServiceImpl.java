package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.TopicDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.backend.core.repository.ModuleRepository;
import com.epam.rd.backend.core.repository.TopicRepository;
import com.epam.rd.backend.core.service.TopicService;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {
    private TopicRepository topicRepository;
    private ModuleRepository moduleRepository;
    private TopicDtoConverter converter;

    @Autowired
    public TopicServiceImpl(TopicRepository repository,
                            ModuleRepository moduleRepository,
                            TopicDtoConverter converter) {
        this.topicRepository = repository;
        this.moduleRepository = moduleRepository;
        this.converter = converter;
    }

    @Override
    public TopicDto createTopic(TopicDtoCreate dto) {
        Topic topic = converter.convert(dto);
        Module module = moduleRepository.findOne(dto.getModuleId());
        module.addTopic(topic);
        return converter.convert(topicRepository.save(topic));
    }

    @Override
    public TopicDto updateTopic(TopicDto dto) {
        Topic topic = converter.convert(dto);
        if (!isTopicExist(topic.getId())) {
            throw new EntityDoesNotExistException(String.format("Topic with ID=%s does not exist", topic.getId()));
        }
        topic.setModule(moduleRepository.findOne(dto.getModuleId()));
        return converter.convert(topicRepository.save(topic));
    }

    private boolean isTopicExist(Long id) {
        return id != null && topicRepository.findOne(id) != null;
    }

    @Override
    public List<TopicDto> getAllTopic() {
        List<Topic> topics = topicRepository.findAll();
        return converter.convertList(topics);
    }

    @Override
    public List<TopicDto> getTopicsByModuleId(Long moduleId) {
        final List<Topic> topics = topicRepository.findTopicsByModuleId(moduleId);
        return converter.convertList(topics);
    }

    @Override
    public TopicDto getTopicById(Long id) {
        Topic topic = topicRepository.findOne(id);
        if (topic == null) {
            throw new EntityDoesNotExistException(String.format("Cannot find topic by ID=%s", id));
        }
        return converter.convert(topic);
    }

    @Override
    public void deleteTopicById(Long id) {
        if (!isTopicExist(id)) {
            throw new EntityDoesNotExistException(String.format("Cannot find topic by ID=%s", id));
        }
        topicRepository.delete(id);
    }

}
