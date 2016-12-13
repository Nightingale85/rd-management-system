package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;
import com.epam.rd.frontend.service.ProgramService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(MockitoJUnitRunner.class)
public class ProgramServiceImplTest {
    @Mock
    private RestOperations restOperations;

    private ProgramService service;

    @Before
    public void set_up() {
        service = new ProgramServiceImpl(restOperations);
    }

    @Test
    public void should_return_program_dto_by_id() {
        //GIVEN
        Long id = 1L;
        String urlTemplate = "/program/{id}";
        ProgramDto actualDto = new ProgramDto();
        actualDto.setId(id);
        when(restOperations.getForObject(eq(urlTemplate), eq(ProgramDto.class), eq(id))).thenReturn(actualDto);
        //WHEN
        ProgramDto expectedDto = service.getProgramById(id);
        //THEN
        assertSame(expectedDto, actualDto);
        verify(restOperations, times(1)).getForObject(eq(urlTemplate), eq(ProgramDto.class), eq(id));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_return_all_programs() {
        //GIVEN
        String urlTemplate = "/programs";
        List<ProgramDto> expectedDtpList = Collections.singletonList(new ProgramDto());
        ResponseEntity<List<ProgramDto>> responseEntity = ResponseEntity.ok(expectedDtpList);
        when(restOperations
                .exchange(eq(urlTemplate), eq(GET), eq(null), eq(new ParameterizedTypeReference<List<ProgramDto>>() {
                }))).thenReturn(responseEntity);
        //WHEN
        List<ProgramDto> actualDtoList = service.getAllPrograms();

        //THEN
        assertEquals(expectedDtpList, actualDtoList);
        verify(restOperations, times(1))
                .exchange(eq(urlTemplate), eq(GET), eq(null), eq(new ParameterizedTypeReference<List<ProgramDto>>() {
                }));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_return_program_id_after_create_new_program() {
        //GIVEN
        String urlTemplate = "/program";
        Long expectedId = 1L;
        ProgramDtoCreate dtoCreate = new ProgramDtoCreate();
        HttpEntity<ProgramDtoCreate> httpEntity = new HttpEntity<>(dtoCreate);
        ProgramDto dto = new ProgramDto();
        dto.setId(expectedId);
        ResponseEntity<ProgramDto> responseEntity = ResponseEntity.status(CREATED).body(dto);
        when(restOperations.exchange(eq(urlTemplate), eq(POST), eq(httpEntity), eq(ProgramDto.class)))
                .thenReturn(responseEntity);
        //WHEN
        Long actualId = service.addProgram(dtoCreate);
        //THEN
        assertEquals(expectedId, actualId);
        verify(restOperations, times(1)).exchange(eq(urlTemplate), eq(POST), eq(httpEntity), eq(ProgramDto.class));
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void should_return_program_dto_after_update_program() {
        //GIVEN
        String urlTemplate = "/program/{id}";
        ProgramDto dto = new ProgramDto();
        dto.setId(1L);
        HttpEntity<ProgramDto> httpEntity = new HttpEntity<>(dto);
        ResponseEntity<ProgramDto> responseEntity = ResponseEntity.ok(dto);
        when(restOperations.exchange(eq(urlTemplate), eq(PUT), eq(httpEntity), eq(ProgramDto.class), eq(dto.getId())))
                .thenReturn(responseEntity);
        //WHEN
        ProgramDto programDto = service.updateProgram(dto);
        //THEN
        assertSame(dto, programDto);
        verify(restOperations, times(1))
                .exchange(eq(urlTemplate), eq(PUT), eq(httpEntity), eq(ProgramDto.class), eq(dto.getId()));
        verifyNoMoreInteractions(restOperations);
    }
}