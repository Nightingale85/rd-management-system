package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.service.TopicService;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.epam.rd.HeaderConstant.HEADER_MODEL_ID;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class TopicController {
    private TopicService service;

    @Autowired
    public TopicController(TopicService service) {
        this.service = service;
    }

    @RequestMapping(value = "/topics", method = GET)
    public ResponseEntity<List<TopicDto>> getAllTopic() {
        List<TopicDto> topicDtoList = service.getAllTopic();
        HttpStatus status = OK;
        if (topicDtoList.isEmpty()) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(topicDtoList);
    }

    @RequestMapping(value = "module/{moduleId}/topics", method = GET)
    public ResponseEntity<List<TopicDto>> getTopicsByModuleId(@PathVariable("moduleId") Long moduleId) {
        List<TopicDto> topicDtoList = service.getTopicsByModuleId(moduleId);
        HttpStatus status = OK;
        if (topicDtoList.isEmpty()) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(topicDtoList);
    }

    @RequestMapping(value = "/topic/{id}", method = GET)
    public ResponseEntity<TopicDto> getTopicById(@PathVariable("id") Long id) {
        TopicDto dto = service.getTopicById(id);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/topic", method = POST)
    public ResponseEntity<TopicDto> addTopic(@RequestBody TopicDtoCreate dtoCreate,
                                             UriComponentsBuilder builder) {
        TopicDto dto = service.createTopic(dtoCreate);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/api/topic/{id}").buildAndExpand(dto.getId()).toUri());
        headers.add(HEADER_MODEL_ID, dto.getId().toString());
        return ResponseEntity.status(CREATED).headers(headers).body(dto);
    }

    @RequestMapping(value = "/topic/{id}", method = PUT)
    public ResponseEntity<TopicDto> updateTopicById(@PathVariable("id") Long id,
                                                    @RequestBody TopicDto topicDto) {
        topicDto.setId(id);
        TopicDto dtoUpdate = service.updateTopic(topicDto);
        return ResponseEntity.ok(dtoUpdate);
    }

    @RequestMapping(value = "/topic/{id}", method = DELETE)
    public ResponseEntity<Void> deleteTopicById(@PathVariable("id") Long id) {
        service.deleteTopicById(id);
        return ResponseEntity.noContent().build();
    }

}
