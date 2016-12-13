package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;
import com.epam.rd.frontend.service.PracticalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Service
public class PracticalTaskServiceImpl implements PracticalTaskService {
    private RestOperations restOperations;

    @Autowired
    public PracticalTaskServiceImpl(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public PracticalTaskDto getPracticalTaskDtoById(Long id) {
        return restOperations.getForObject("/task/{id}", PracticalTaskDto.class, id);
    }


    @Override
    public PracticalTaskDto getPracticalTaskDtoByTopicId(Long topicId) {
        return restOperations.getForObject("/topic/{topicId}/task", PracticalTaskDto.class, topicId);
    }

    @Override
    public Long addPracticalTask(PracticalTaskDtoCreate dtoCreate) {
        ResponseEntity<PracticalTaskDto> responseEntity = restOperations
                .exchange("/task", POST, new HttpEntity<>(dtoCreate), PracticalTaskDto.class);
        return responseEntity.getBody().getId();
    }

    @Override
    public PracticalTaskDto updatePracticalTask(PracticalTaskDto taskDto) {
        HttpEntity<PracticalTaskDto> httpEntity = new HttpEntity<>(taskDto);
        ResponseEntity<PracticalTaskDto> responseEntity = restOperations
                .exchange("/task/{id}", PUT, httpEntity, PracticalTaskDto.class, taskDto.getId());
        return responseEntity.getBody();
    }

    @Override
    public void deletePracticalTaskById(Long id) {
        restOperations.delete("/task/{id}", id);
    }
}
