package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.LectureDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.exception.EntityIsAlreadyExistException;
import com.epam.rd.backend.core.model.Lecture;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.backend.core.repository.LectureRepository;
import com.epam.rd.backend.core.repository.TopicRepository;
import com.epam.rd.backend.core.service.LectureService;
import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.LectureDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Service
public class LectureServiceImpl implements LectureService {
    private LectureRepository lectureRepository;
    private TopicRepository topicRepository;
    private LectureDtoConverter converter;

    @Autowired
    public LectureServiceImpl(LectureRepository lectureRepository,
                              TopicRepository topicRepository,
                              LectureDtoConverter converter) {
        this.lectureRepository = lectureRepository;
        this.topicRepository = topicRepository;
        this.converter = converter;
    }

    @Override
    public LectureDto createLecture(LectureDtoCreate dtoCreate) {
        Lecture lecture = converter.convert(dtoCreate);
        Topic topic = getTopicById(dtoCreate.getTopicId());
        if (!isNull(topic.getLecture())) {
            throw new EntityIsAlreadyExistException(
                    format("Topic with ID=%d already contains lecture with ID=%d",
                           topic.getId(),
                           topic.getLecture().getId()));
        }
        topic.setLecture(lecture);
        return converter.convert(lectureRepository.save(lecture));
    }

    private Topic getTopicById(Long topicId) {
        Topic topic = topicRepository.findOne(topicId);
        if (isNull(topic)) {
            throw new EntityDoesNotExistException(
                    format("Topic with ID=%d does not exist", topicId));
        }
        return topic;
    }

    @Override
    public LectureDto updateLecture(LectureDto dto) {
        Lecture lecture = converter.convert(dto);
        if (!isLectureExist(lecture.getId())) {
            throw new EntityDoesNotExistException(
                    format("Lecture with ID=%s does not exist", lecture.getId()));
        }
        Topic topic = getTopicById(dto.getTopicId());
        if (isNull(topic.getLecture())) {
            throw new EntityDoesNotExistException(
                    format("Topic with ID=%d doesn't contains lecture with ID=%d",
                           topic.getId(),
                           dto.getId()));
        }
        topic.setLecture(lecture);
        return converter.convert(lectureRepository.save(lecture));
    }

    private boolean isLectureExist(Long id) {
        return !isNull(id) && !isNull(lectureRepository.findOne(id));
    }

    @Override
    @Transactional
    public List<LectureDto> getAllLecture() {
        final List<Lecture> lectures = lectureRepository.findAll();
        return lectures.stream()
                       .map(lecture -> converter.convert(lecture))
                       .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LectureDto getLectureById(Long id) {
        Lecture lecture = lectureRepository.findOne(id);
        if (isNull(lecture)) {
            throw new EntityDoesNotExistException(format("Cannot find lecture by ID=%s", id));
        }
        return converter.convert(lecture);
    }

    @Override
    public void deleteLectureById(Long id) {
        if (!isLectureExist(id)) {
            throw new EntityDoesNotExistException(format("Cannot find lecture by ID=%s", id));
        }
        lectureRepository.delete(id);
    }

    @Override
    @Transactional
    public LectureDto getLectureByTopicId(Long topicId) {
        Lecture lecture = lectureRepository.findLectureByTopicId(topicId);
        if (isNull(lecture)) {
            throw new EntityDoesNotExistException(format("Cannot find lecture by Topic ID=%s",
                                                         topicId));
        }
        return converter.convert(lecture);
    }
}
