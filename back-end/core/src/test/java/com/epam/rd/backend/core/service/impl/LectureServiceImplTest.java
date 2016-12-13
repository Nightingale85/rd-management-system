package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.LectureDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.model.Lecture;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.backend.core.repository.LectureRepository;
import com.epam.rd.backend.core.repository.TopicRepository;
import com.epam.rd.backend.core.service.LectureService;
import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.LectureDtoCreate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LectureServiceImplTest {
    private static final String LECTURE_TITLE = "New Lecture";
    private static final long LECTURE_ID = 1L;
    private static final Long TOPIC_ID = 1L;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureDtoConverter converter;

    private LectureService service;
    private LectureDto lectureDto;
    private Lecture lecturePersist;

    @Before
    public void set_up() {
        service = new LectureServiceImpl(lectureRepository, topicRepository, converter);
        lectureDto = new LectureDto();
        lectureDto.setId(LECTURE_ID);
        lectureDto.setTitle(LECTURE_TITLE);
        lectureDto.setTopicId(TOPIC_ID);
        lecturePersist = new Lecture();
        lecturePersist.setId(LECTURE_ID);
        lecturePersist.setName(LECTURE_TITLE);
    }

    @Test
    public void should_return_lecture_dto_after_create_new_lecture() {
        //GIVEN
        LectureDtoCreate dtoCreate = new LectureDtoCreate();
        dtoCreate.setTitle(LECTURE_TITLE);
        dtoCreate.setTopicId(TOPIC_ID);
        Lecture newLecture = new Lecture();
        newLecture.setName(LECTURE_TITLE);
        Topic topicPersist = new Topic();
        topicPersist.setId(TOPIC_ID);
        when(converter.convert(dtoCreate)).thenReturn(newLecture);
        when(lectureRepository.save(newLecture)).thenReturn(lecturePersist);
        when(topicRepository.findOne(TOPIC_ID)).thenReturn(topicPersist);
        when(converter.convert(lecturePersist)).thenReturn(lectureDto);
        //WHEN
        LectureDto dto = service.createLecture(dtoCreate);
        //THEN
        assertSame("Should return lecture DTO", lectureDto, dto);
        verify(converter, times(1)).convert(dtoCreate);
        verify(lectureRepository, times(1)).save(newLecture);
        verify(converter, times(1)).convert(lecturePersist);
    }

    @Test
    public void should_update_lecture() {
        //GIVEN
        Lecture lectureUpdate = new Lecture();
        lectureUpdate.setId(1L);
        lectureUpdate.setName("Update Lecture Title");
        LectureDto lectureDtoUpdate = new LectureDto();
        lectureDtoUpdate.setId(LECTURE_ID);
        lectureDtoUpdate.setTitle("Update Lecture Title");
        lectureDtoUpdate.setTopicId(TOPIC_ID);
        Topic topicPersist = new Topic();
        topicPersist.setId(TOPIC_ID);
        topicPersist.setLecture(lecturePersist);
        when(converter.convert(lectureDto)).thenReturn(lecturePersist);
        when(lectureRepository.findOne(LECTURE_ID)).thenReturn(lecturePersist);
        when(topicRepository.findOne(TOPIC_ID)).thenReturn(topicPersist);
        when(lectureRepository.save(lecturePersist)).thenReturn(lectureUpdate);
        when(converter.convert(lectureUpdate)).thenReturn(lectureDtoUpdate);
        //WHEN
        LectureDto dto = service.updateLecture(lectureDto);
        //THEN
        assertEquals("Should update and return Lecture DTO", lectureDtoUpdate.getTitle(), dto.getTitle());
        verify(converter, times(1)).convert(lectureDto);
        verify(lectureRepository, times(1)).save(lecturePersist);
        verify(converter, times(1)).convert(lectureUpdate);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_throw_exception_if_try_update_not_exist_lecture() {
        //GIVEN
        when(converter.convert(lectureDto)).thenReturn(lecturePersist);
        //WHEN
        service.updateLecture(lectureDto);
        //THEN
        fail("Expected EntityDoesNotExistException, but exception not throw");
    }

    @Test
    public void should_return_list_of_lecture() {
        //GIVEN
        List<Lecture> lectures = Arrays.asList(lecturePersist, lecturePersist);
        when(lectureRepository.findAll()).thenReturn(lectures);
        //WHEN
        List<LectureDto> lectureDtoList = service.getAllLecture();
        //THEN
        assertEquals(lectures.size(), lectureDtoList.size());
    }

    @Test
    public void should_return_lecture_dto_by_lecture_id() {
        //GIVEN
        when(lectureRepository.findOne(LECTURE_ID)).thenReturn(lecturePersist);
        when(converter.convert(lecturePersist)).thenReturn(lectureDto);
        //WHEN
        LectureDto dto = service.getLectureById(LECTURE_ID);
        //THEN
        assertSame("Should return lecture DTO", lectureDto, dto);
        verify(lectureRepository, times(1)).findOne(LECTURE_ID);
        verify(converter, times(1)).convert(lecturePersist);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_throw_exception_if_lecture_not_found() {
        //GIVEN
        when(lectureRepository.findOne(LECTURE_ID)).thenReturn(null);
        //WHEN
        service.getLectureById(LECTURE_ID);
        //THEN
        fail("Expected EntityDoesNotExistException, but exception not throw");
    }

    @Test
    public void should_delete_lecture_by_id() {
        //GIVEN
        when(lectureRepository.findOne(LECTURE_ID)).thenReturn(lecturePersist);
        //WHEN
        service.deleteLectureById(LECTURE_ID);
        //THEN
        verify(lectureRepository, times(1)).findOne(LECTURE_ID);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_throw_exception_if_try_delete_not_exist_lecture() {
        //GIVEN
        when(lectureRepository.findOne(LECTURE_ID)).thenReturn(null);
        //WHEN
        service.deleteLectureById(LECTURE_ID);
        //THEN
        fail("Expected EntityDoesNotExistException, but exception not throw");
    }

    @Test
    public void should_return_lecture_dto_by_topic_id() {
        //GIVEN
        when(lectureRepository.findLectureByTopicId(TOPIC_ID)).thenReturn(lecturePersist);
        when(converter.convert(lecturePersist)).thenReturn(lectureDto);
        //WHEN
        LectureDto dto = service.getLectureByTopicId(TOPIC_ID);
        //THEN
        assertSame("Should return lecture DTO", lectureDto, dto);
        verify(lectureRepository, times(1)).findLectureByTopicId(TOPIC_ID);
        verify(converter, times(1)).convert(lecturePersist);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_throw_exception_if_lecture_by_topic_id_not_found() {
        //GIVEN
        when(lectureRepository.findLectureByTopicId(TOPIC_ID)).thenReturn(null);
        //WHEN
        service.getLectureByTopicId(TOPIC_ID);
        //THEN
        fail("Expected EntityDoesNotExistException, but exception not throw");
    }
}