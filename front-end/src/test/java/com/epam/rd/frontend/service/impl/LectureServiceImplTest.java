package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.LectureDtoCreate;
import com.epam.rd.frontend.service.LectureService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(MockitoJUnitRunner.class)
public class LectureServiceImplTest {
    @Mock
    private RestOperations restOperations;

    private LectureService service;

    @Before
    public void set_up() {
        service = new LectureServiceImpl(restOperations);
    }

    @Test
    public void should_return_lecture_dto_by_id() throws JsonProcessingException {
        //GIVEN
        String urlTemplate = "/lecture/{id}";
        Long id = 1L;
        when(restOperations.getForObject(eq(urlTemplate), eq(LectureDto.class), eq(id)))
                .thenReturn(new LectureDto());
        //WHEN
        service.getLectureById(id);
        //THEN
        verify(restOperations, times(1))
                .getForObject(eq(urlTemplate), eq(LectureDto.class), eq(id));
        verifyNoMoreInteractions(restOperations);
    }


    @Test
    public void should_return_lecture_dto_by_topic_id() throws JsonProcessingException {
        //GIVEN
        String urlTemplate = "/topic/{topicId}/lecture";
        Long topicId = 1L;
        when(restOperations.getForObject(eq(urlTemplate), eq(LectureDto.class), eq(topicId)))
                .thenReturn(new LectureDto());
        //WHEN
        service.getLectureDtoByTopicId(topicId);
        //THEN
        verify(restOperations, times(1))
                .getForObject(eq(urlTemplate), eq(LectureDto.class), eq(topicId));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_return_lecture_id_after_create_new_lecture() {
        //GIVEN
        String urlTemplate = "/lecture";
        Long expectedId = 1L;
        LectureDtoCreate dtoCreate = new LectureDtoCreate();
        HttpEntity<LectureDtoCreate> httpEntity = new HttpEntity<>(dtoCreate);
        LectureDto lectureDto = new LectureDto();
        lectureDto.setId(expectedId);
        ResponseEntity<LectureDto> responseEntity = new ResponseEntity<>(lectureDto, CREATED);
        when(restOperations.exchange(eq(urlTemplate), eq(POST), eq(httpEntity), eq(LectureDto.class)))
                .thenReturn(responseEntity);
        //WHEN
        Long actualId = service.addLecture(dtoCreate);
        //THEN
        assertEquals(expectedId, actualId);
        verify(restOperations, times(1)).exchange(eq(urlTemplate), eq(POST), eq(httpEntity), eq(LectureDto.class));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_return_lecture_dto_after_update_lecture() throws JsonProcessingException {
        //GIVEN
        String urlTemplate = "/lecture/{id}";
        LectureDto dto = new LectureDto();
        dto.setId(1L);
        ResponseEntity<LectureDto> responseEntity = ResponseEntity.ok(dto);
        HttpEntity<LectureDto> httpEntity = new HttpEntity<>(dto);
        when(restOperations.exchange(eq(urlTemplate), eq(PUT), eq(httpEntity), eq(LectureDto.class), eq(dto.getId())))
                .thenReturn(responseEntity);
        //WHEN
        LectureDto taskDto = service.updateLecture(dto);
        //THEN
        assertSame(dto, taskDto);
        verify(restOperations, times(1))
                .exchange(eq(urlTemplate), eq(PUT), eq(httpEntity), eq(LectureDto.class), eq(dto.getId()));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_delete_lecture_by_id() {
        //GIVEN
        String urlTemplate = "/lecture/{id}";
        Long id = 1L;
        //WHEN
        service.deleteLecture(id);
        //THEN
        verify(restOperations, times(1)).delete(eq(urlTemplate), eq(id));
        verifyNoMoreInteractions(restOperations);
    }
}