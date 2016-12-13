package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;
import com.epam.rd.frontend.service.PracticalTaskService;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(MockitoJUnitRunner.class)
public class PracticalTaskServiceImplTest {
    private static final long TOPIC_ID = 1L;

    @Mock
    private RestOperations restOperations;

    private PracticalTaskService service;

    @Before
    public void set_up() {
        service = new PracticalTaskServiceImpl(restOperations);
    }

    @Test
    public void should_return_practical_task_by_topic_id() {
        //GIVEN
        String urlTemplate = "/topic/{topicId}/task";
        Long topicId = 1L;
        when(restOperations.getForObject(eq(urlTemplate), eq(PracticalTaskDto.class), eq(topicId)))
                .thenReturn(new PracticalTaskDto());
        //WHEN
        service.getPracticalTaskDtoByTopicId(TOPIC_ID);
        //THEN
        verify(restOperations, times(1)).getForObject(eq(urlTemplate), eq(PracticalTaskDto.class), eq(topicId));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_return_practical_task_id_after_create_new_practical_task() {
        //GIVEN
        String urlTemplate = "/task";
        Long expectedId = 1L;
        PracticalTaskDtoCreate dtoCreate = new PracticalTaskDtoCreate();
        HttpEntity<PracticalTaskDtoCreate> httpEntity = new HttpEntity<>(dtoCreate);
        PracticalTaskDto taskDtoDto = new PracticalTaskDto();
        taskDtoDto.setId(expectedId);
        ResponseEntity<PracticalTaskDto> responseEntity = new ResponseEntity<>(taskDtoDto, CREATED);
        when(restOperations.exchange(eq(urlTemplate), eq(POST), eq(httpEntity), eq(PracticalTaskDto.class)))
                .thenReturn(responseEntity);
        //WHEN
        Long actualId = service.addPracticalTask(dtoCreate);
        //THEN
        assertEquals(expectedId, actualId);
        verify(restOperations, times(1)).exchange(eq(urlTemplate), eq(POST), eq(httpEntity),
                                                  eq(PracticalTaskDto.class));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_return_practical_task_dto_after_update_practical_task() {
        //GIVEN
        String urlTemplate = "/task/{id}";
        PracticalTaskDto dto = new PracticalTaskDto();
        HttpEntity<PracticalTaskDto> entity = new HttpEntity<>(dto);
        ResponseEntity<PracticalTaskDto> responseEntity = ResponseEntity.ok(dto);
        when(restOperations.exchange(eq(urlTemplate), eq(PUT), eq(entity), eq(PracticalTaskDto.class), eq(dto.getId())))
                .thenReturn(responseEntity);
        //WHEN
        PracticalTaskDto taskDto = service.updatePracticalTask(dto);
        //THEN
        assertSame(dto, taskDto);
        verify(restOperations, times(1))
                .exchange(eq(urlTemplate), eq(PUT), eq(entity), eq(PracticalTaskDto.class), eq(dto.getId()));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_delete_practical_task_by_id() {
        //GIVEN
        String urlTemplate = "/task/{id}";
        Long id = 1L;
        //WHEN
        service.deletePracticalTaskById(id);
        //THEN
        verify(restOperations, times(1)).delete(eq(urlTemplate), eq(id));
        verifyNoMoreInteractions(restOperations);
    }
}