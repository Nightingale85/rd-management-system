package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.service.PracticalTaskService;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;
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
public class PracticalTaskController {
    private PracticalTaskService service;

    @Autowired
    public PracticalTaskController(PracticalTaskService service) {
        this.service = service;
    }

    @RequestMapping(value = "/tasks", method = GET)
    public ResponseEntity<List<PracticalTaskDto>> getAllPracticalTask() {
        List<PracticalTaskDto> taskDtoList = service.getAllPracticalTask();
        HttpStatus status = OK;
        if (taskDtoList.isEmpty()) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(taskDtoList);
    }

    @RequestMapping(value = "/task/{id}", method = GET)
    public ResponseEntity<PracticalTaskDto> getPracticalTaskById(@PathVariable("id") Long id) {
        PracticalTaskDto dto = service.getPracticalTaskById(id);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/topic/{topicId}/task", method = GET)
    public ResponseEntity<PracticalTaskDto> getPracticalTaskByTopicId(@PathVariable("topicId") Long topicId) {
        PracticalTaskDto dto = service.getPracticalTaskByTopicId(topicId);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/task", method = POST)
    public ResponseEntity<PracticalTaskDto> addPracticalTask(@RequestBody PracticalTaskDtoCreate dtoCreate,
                                                             UriComponentsBuilder builder) {
        PracticalTaskDto dto = service.createPracticalTask(dtoCreate);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/task/{id}").buildAndExpand(dto.getId()).toUri());
        headers.add(HEADER_MODEL_ID, dto.getId().toString());
        return ResponseEntity.status(CREATED).headers(headers).body(dto);
    }

    @RequestMapping(value = "/task/{id}", method = PUT)
    public ResponseEntity<PracticalTaskDto> updatePracticalTaskById(@PathVariable("id") Long id,
                                                                    @RequestBody PracticalTaskDto taskDto) {
        taskDto.setId(id);
        PracticalTaskDto dtoUpdate = service.updatePracticalTask(taskDto);
        return ResponseEntity.ok(dtoUpdate);
    }

    @RequestMapping(value = "/task/{id}", method = DELETE)
    public ResponseEntity<Void> deletePracticalTaskById(@PathVariable("id") Long id) {
        service.deletePracticalTaskById(id);
        return ResponseEntity.noContent().build();
    }

}
