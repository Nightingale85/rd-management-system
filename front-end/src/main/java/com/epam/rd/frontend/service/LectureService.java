package com.epam.rd.frontend.service;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.LectureDtoCreate;
import org.springframework.security.access.annotation.Secured;

public interface LectureService {

    LectureDto getLectureById(Long id);

    LectureDto getLectureDtoByTopicId(Long topicId);

    @Secured("ROLE_LECTURER")
    Long addLecture(LectureDtoCreate dtoCreate);

    @Secured("ROLE_LECTURER")
    LectureDto updateLecture(LectureDto lectureDto);

    @Secured("ROLE_LECTURER")
    void deleteLecture(Long id);
}
