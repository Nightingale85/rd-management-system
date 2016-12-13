package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.service.LectureService;
import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.LectureDtoCreate;
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
public class LectureController {
    private LectureService service;

    @Autowired
    public LectureController(LectureService service) {
        this.service = service;
    }

    @RequestMapping(value = "/lectures", method = GET)
    public ResponseEntity<List<LectureDto>> getAllLecture() {
        List<LectureDto> lectureDtoList = service.getAllLecture();
        HttpStatus status = OK;
        if (lectureDtoList.isEmpty()) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(lectureDtoList);
    }

    @RequestMapping(value = "/lecture/{id}", method = GET)
    public ResponseEntity<LectureDto> getLectureById(@PathVariable("id") Long id) {
        LectureDto dto = service.getLectureById(id);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/topic/{topicId}/lecture", method = GET)
    public ResponseEntity<LectureDto> getLectureByTopicId(@PathVariable("topicId") Long topicId) {
        LectureDto dto = service.getLectureByTopicId(topicId);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/lecture", method = POST)
    public ResponseEntity<LectureDto> addLecture(@RequestBody LectureDtoCreate dtoCreate,
                                                 UriComponentsBuilder builder) {
        LectureDto dto = service.createLecture(dtoCreate);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/lecture/{id}").buildAndExpand(dto.getId()).toUri());
        headers.add(HEADER_MODEL_ID, dto.getId().toString());
        return ResponseEntity.status(CREATED).headers(headers).body(dto);
    }

    @RequestMapping(value = "/lecture/{id}", method = PUT)
    public ResponseEntity<LectureDto> updateLectureById(@PathVariable("id") Long id,
                                                        @RequestBody LectureDto lectureDto) {
        lectureDto.setId(id);
        LectureDto dtoUpdate = service.updateLecture(lectureDto);
        return ResponseEntity.ok(dtoUpdate);
    }

    @RequestMapping(value = "/lecture/{id}", method = DELETE)
    public ResponseEntity<Void> deleteLectureById(@PathVariable("id") Long id) {
        service.deleteLectureById(id);
        return ResponseEntity.noContent().build();
    }
}
