package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.MarkDtoCreate;
import com.epam.rd.dto.RoleDto;
import com.epam.rd.frontend.service.MarkService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.CREATED;


@RunWith(MockitoJUnitRunner.class)
public class MarkServiceImplTest {
    @Mock
    private RestOperations restOperations;

    private MarkService markService;
    private List<MarkDto> markDtos;
    private MarkDto markDto;

    @Before
    public void set_up() {
        markService = new MarkServiceImpl(restOperations);
        markDto = new MarkDto();
        markDto.setId(1L);
        markDto.setPracticalTaskId(1L);
        markDto.setMenteeEmail("mentee@email.com");
        markDto.setEstimatorEmail("mentor@email.com");
        markDto.setEstimatorRole(RoleDto.valueOf("MENTOR"));
        markDto.setMark(10);
        markDto.setFeedback("OK");

        markDtos = new ArrayList<>();
        markDtos.add(markDto);
    }

    @Test
    public void should_return_mark_dto_by_id() {
        //GIVEN
        String urlTemplate = "/mark/{id}";
        Long id = 1L;
        when(restOperations.getForObject(eq(urlTemplate), eq(MarkDto.class), eq(id)))
                .thenReturn(new MarkDto());
        //WHEN
        markService.getMarkById(id);
        //THEN
        verify(restOperations, times(1))
                .getForObject(eq(urlTemplate), eq(MarkDto.class), eq(id));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_return_mark_id_after_create_new_mark() {
        //GIVEN
        String urlTemplate = "/mark";
        Long expectedId = 1L;
        MarkDtoCreate dtoCreate = new MarkDtoCreate();
        HttpEntity<MarkDtoCreate> httpEntity = new HttpEntity<>(dtoCreate);
        MarkDto markDto = new MarkDto();
        markDto.setId(expectedId);
        ResponseEntity<MarkDto> responseEntity = new ResponseEntity<>(markDto, CREATED);
        when(restOperations.exchange(eq(urlTemplate), eq(POST), eq(httpEntity), eq(MarkDto.class)))
                .thenReturn(responseEntity);
        //WHEN
        Long actualId = markService.addMark(dtoCreate);
        //THEN
        assertEquals(expectedId, actualId);
        verify(restOperations, times(1)).exchange(eq(urlTemplate), eq(POST), eq(httpEntity), eq(MarkDto.class));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_return_mark_dto_after_update_mark() {
        //GIVEN
        String urlTemplate = "/mark/{id}";
        MarkDto dto = new MarkDto();
        dto.setId(1L);
        ResponseEntity<MarkDto> responseEntity = ResponseEntity.ok(dto);
        HttpEntity<MarkDto> httpEntity = new HttpEntity<>(dto);
        when(restOperations.exchange(eq(urlTemplate), eq(PUT), eq(httpEntity), eq(MarkDto.class), eq(dto.getId())))
                .thenReturn(responseEntity);
        //WHEN
        MarkDto taskDto = markService.updateMark(dto);
        //THEN
        assertSame(dto, taskDto);
        verify(restOperations, times(1))
                .exchange(eq(urlTemplate), eq(PUT), eq(httpEntity), eq(MarkDto.class), eq(dto.getId()));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_delete_mark_by_id() {
        //GIVEN
        String urlTemplate = "/mark/{id}";
        Long id = 1L;
        //WHEN
        markService.deleteMarkById(id);
        //THEN
        verify(restOperations, times(1)).delete(eq(urlTemplate), eq(id));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_return_list_mark_dto_by_mentee_email() {
        //GIVEN
        ResponseEntity<List<MarkDto>> marksResponseEntity = new ResponseEntity<>(markDtos, HttpStatus.OK);
        when(restOperations.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<MarkDto>>() {
                }),
                anyLong()
        )).thenReturn(marksResponseEntity);

        //WHEN
        List<MarkDto> markDtoList = markService.getMarksByMenteeEmail("mentee@email.com");

        //THEN
        verify(restOperations).exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<MarkDto>>() {
                }),
                anyLong());
        verifyNoMoreInteractions(restOperations);
        assertEquals(markDtos, markDtoList);
    }
}
