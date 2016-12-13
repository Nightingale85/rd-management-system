package com.epam.rd.backend.core.service;

import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;

import java.util.List;

public interface TopicService {

    TopicDto createTopic(TopicDtoCreate dto);

    TopicDto updateTopic(TopicDto dto);

    List<TopicDto> getAllTopic();

    List<TopicDto> getTopicsByModuleId(Long moduleId);

    TopicDto getTopicById(Long id);

    void deleteTopicById(Long iD);
}
