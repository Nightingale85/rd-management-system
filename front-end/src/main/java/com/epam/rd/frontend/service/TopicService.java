package com.epam.rd.frontend.service;

import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface TopicService {

    TopicDto getTopicById(Long topicId);

    List<TopicDto> getTopicsByModuleId(Long moduleId);

    @Secured("ROLE_MANAGER")
    Long addTopic(TopicDtoCreate dtoCreate);

    @Secured("ROLE_MANAGER")
    TopicDto updateTopic(TopicDto topicDto);

}
