package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.LectureDtoCreate;
import com.epam.rd.frontend.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Service
public class LectureServiceImpl implements LectureService {
    private RestOperations restOperations;

    @Autowired
    public LectureServiceImpl(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public LectureDto getLectureById(Long id) {
        return restOperations.getForObject("/lecture/{id}", LectureDto.class, id);
    }

    @Override
    public LectureDto getLectureDtoByTopicId(Long topicId) {
        return restOperations.getForObject("/topic/{topicId}/lecture", LectureDto.class, topicId);
    }

    @Override
    public Long addLecture(LectureDtoCreate dtoCreate) {
        ResponseEntity<LectureDto> responseEntity = restOperations
                .exchange("/lecture", POST, new HttpEntity<>(dtoCreate), LectureDto.class);
        return responseEntity.getBody().getId();
    }

    @Override
    public LectureDto updateLecture(LectureDto lectureDto) {
        HttpEntity<LectureDto> httpEntity = new HttpEntity<>(lectureDto);
        ResponseEntity<LectureDto> responseEntity = restOperations
                .exchange("/lecture/{id}", PUT, httpEntity, LectureDto.class, lectureDto.getId());
        return responseEntity.getBody();
    }

    @Override
    public void deleteLecture(Long id) {
        restOperations.delete("/lecture/{id}", id);
    }
}
