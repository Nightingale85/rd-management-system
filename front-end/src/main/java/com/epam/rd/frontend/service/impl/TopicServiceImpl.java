package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;
import com.epam.rd.frontend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Service
public class TopicServiceImpl implements TopicService {

    private RestOperations restOperations;

    @Autowired
    public TopicServiceImpl(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public TopicDto getTopicById(Long topicId) {
        return restOperations.getForObject("/topic/{topicId}", TopicDto.class, topicId);
    }

    @Override
    public Long addTopic(TopicDtoCreate dtoCreate) {
        ResponseEntity<TopicDto> responseEntity = restOperations
                .exchange("/topic", POST, new HttpEntity<>(dtoCreate), TopicDto.class);
        return responseEntity.getBody().getId();
    }


    @Override
    public TopicDto updateTopic(TopicDto topicDto) {
        HttpEntity<TopicDto> entity = new HttpEntity<>(topicDto);
        ResponseEntity<TopicDto> responseEntity = restOperations
                .exchange("/topic/{id}",
                        PUT,
                        entity,
                        TopicDto.class,
                        topicDto.getId());
        return responseEntity.getBody();
    }

    @Override
    public List<TopicDto> getTopicsByModuleId(Long moduleId) {
        return restOperations
                .exchange("/module/{moduleId}/topics", GET, null, new ParameterizedTypeReference<List<TopicDto>>() {
                }, moduleId)
                .getBody();
    }

}
