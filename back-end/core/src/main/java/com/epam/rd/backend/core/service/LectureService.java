package com.epam.rd.backend.core.service;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.LectureDtoCreate;

import java.util.List;

public interface LectureService {
    LectureDto createLecture(LectureDtoCreate dtoCreate);

    LectureDto updateLecture(LectureDto dto);

    List<LectureDto> getAllLecture();

    LectureDto getLectureById(Long id);

    void deleteLectureById(Long id);

    LectureDto getLectureByTopicId(Long topicId);
}
