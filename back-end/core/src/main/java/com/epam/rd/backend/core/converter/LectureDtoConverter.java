package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.Lecture;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.LectureDtoCreate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LectureDtoConverter {
    public LectureDto convert(Lecture lecture) {
        LectureDto dto = new LectureDto();
        dto.setId(lecture.getId());
        dto.setTitle(lecture.getName());
        dto.setDateOfLecture(lecture.getDateStart());
        dto.setTypeOfPlace(lecture.getTypeOfPlace());
        dto.setDevice(lecture.getDevice());
        dto.setLinkTopicEpam(lecture.getLinkTopicEpam());
        dto.setLinkYoutube(lecture.getLinkYoutube());
        dto.setLinkVideoPortalEpam(lecture.getLinkVideoPortalEpam());
        dto.setAgenda(lecture.getAgenda());
        dto.setDescription(lecture.getDescription());
        dto.setTopicId(getTopicId(lecture));
        return dto;
    }

    private Long getTopicId(Lecture lecture) {
        Topic topic = lecture.getTopic();
        return topic != null ? topic.getId() : null;
    }

    public Lecture convert(LectureDto dto) {
        Objects.requireNonNull(dto, "Lecture DTO cannot be NULL");
        Lecture lecture = new Lecture();
        lecture.setId(dto.getId());
        lecture.setName(dto.getTitle());
        lecture.setDateStart(dto.getDateOfLecture());
        lecture.setTypeOfPlace(dto.getTypeOfPlace());
        lecture.setDeviceList(dto.getDevice());
        lecture.setLinkTopicEpam(dto.getLinkTopicEpam());
        lecture.setLinkYoutube(dto.getLinkYoutube());
        lecture.setLinkVideoPortalEpam(dto.getLinkVideoPortalEpam());
        lecture.setAgenda(dto.getAgenda());
        lecture.setDescription(dto.getDescription());
        return lecture;
    }

    public Lecture convert(LectureDtoCreate dto) {
        Objects.requireNonNull(dto, "Lecture DTO cannot be NULL");
        Lecture lecture = new Lecture();
        lecture.setName(dto.getTitle());
        lecture.setDateStart(dto.getDateOfLecture());
        lecture.setTypeOfPlace(dto.getTypeOfPlace());
        lecture.setDeviceList(dto.getDevice());
        lecture.setLinkTopicEpam(dto.getLinkTopicEpam());
        lecture.setLinkYoutube(dto.getLinkYoutube());
        lecture.setLinkVideoPortalEpam(dto.getLinkVideoPortalEpam());
        lecture.setAgenda(dto.getAgenda());
        lecture.setDescription(dto.getDescription());
        return lecture;
    }
}
