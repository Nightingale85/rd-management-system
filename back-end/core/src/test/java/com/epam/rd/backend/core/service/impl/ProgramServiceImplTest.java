package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.ProgramDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.model.Program;
import com.epam.rd.backend.core.repository.ProgramRepository;
import com.epam.rd.backend.core.service.ProgramService;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Oleksii_Ushakov
 */
@RunWith(MockitoJUnitRunner.class)
public class ProgramServiceImplTest {
    private static final Long ID = 999L;
    private static final String TEST_TITLE = "Test name";
    private static final LocalDate DATE_OF_LECTURE = LocalDate.of(2000, 2, 2);
    private static final String DESCRIPTION = "Good test";

    private ProgramDtoCreate programDtoCreate;
    private Program program;
    private ProgramDto programDto;

    @Mock
    private ProgramRepository repositoryMock;

    @Mock
    private ProgramDtoConverter converterMock;

    private ProgramService service;

    @Before
    public void init() {
        service = new ProgramServiceImpl(repositoryMock, converterMock);

        programDtoCreate = new ProgramDtoCreate();
        programDtoCreate.setTitle(TEST_TITLE);
        programDtoCreate.setDescription(DESCRIPTION);
        programDtoCreate.setStartDate(DATE_OF_LECTURE);
        programDtoCreate.setEndDate(DATE_OF_LECTURE);

        program = new Program();
        program.setId(ID);
        program.setName(TEST_TITLE);
        program.setLinkToDescription(DESCRIPTION);
        program.setDateStart(DATE_OF_LECTURE);
        program.setDateEnd(DATE_OF_LECTURE);

        programDto = new ProgramDto();
        programDto.setTitle(TEST_TITLE);
        programDto.setDescription(DESCRIPTION);
        programDto.setStartDate(DATE_OF_LECTURE);
        programDto.setEndDate(DATE_OF_LECTURE);
    }

    @Test
    public void should_create_program() {
        //GIVEN
        when(converterMock.convert(programDtoCreate)).thenReturn(program);
        when(repositoryMock.save(program)).thenReturn(program);
        when(converterMock.convert(program)).thenReturn(programDto);

        //WHEN
        ProgramDto testProgramDto = service.createProgram(programDtoCreate);

        //THEN
        verify(converterMock).convert(programDtoCreate);
        verify(repositoryMock).save(this.program);
        verify(converterMock).convert(this.program);
        verifyNoMoreInteractions(converterMock, repositoryMock);

        assertEquals(testProgramDto, programDto);
    }

    @Test
    public void should_update_program() {
        //GIVEN
        when(converterMock.convert(programDto)).thenReturn(program);
        when(repositoryMock.findOne(ID)).thenReturn(program);
        when(repositoryMock.save(program)).thenReturn(program);
        when(converterMock.convert(program)).thenReturn(programDto);

        //WHEN
        ProgramDto testProgramDto = service.updateProgram(this.programDto);

        //THEN
        verify(converterMock).convert(this.programDto);
        verify(repositoryMock).findOne(ID);
        verify(repositoryMock).save(program);
        verify(converterMock).convert(program);
        verifyNoMoreInteractions(converterMock, repositoryMock);

        assertEquals(testProgramDto, programDto);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_throw_exception_in_update_program() {
        //GIVEN
        when(converterMock.convert(programDto)).thenReturn(program);

        //WHEN
        ProgramDto testProgramDto = service.updateProgram(this.programDto);

        //THEN
        verify(converterMock).convert(programDto);
        verify(repositoryMock).findOne(ID);
        verifyNoMoreInteractions(converterMock, repositoryMock);

        assertEquals(testProgramDto, programDto);
    }

    @Test
    public void should_get_all_program() {
        //GIVEN
        when(repositoryMock.findAll()).thenReturn(Collections.singletonList(program));
        when(converterMock.convert(program)).thenReturn(programDto);

        //WHEN
        List<ProgramDto> testServiceAllProgram = service.getAllProgram();

        //THEN
        verify(repositoryMock).findAll();
        verify(converterMock).convert(program);
        verifyNoMoreInteractions(converterMock, repositoryMock);

        assertEquals(testServiceAllProgram, (Collections.singletonList(programDto)));
    }

    @Test
    public void should_get_program_by_id() {
        //GIVEN
        when(repositoryMock.findOne(ID)).thenReturn(program);
        when(converterMock.convert(program)).thenReturn(programDto);

        //WHEN
        ProgramDto testProgramDto = service.getProgramById(ID);

        //THEN
        verify(repositoryMock).findOne(ID);
        verify(converterMock).convert(program);

        assertEquals(testProgramDto, programDto);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_throw_exception_in_get_program_by_id() {
        //WHEN
        service.getProgramById(ID);

        //THEN
        verify(repositoryMock).findOne(ID);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void should_delete_program_by_id() {
        //GIVEN
        when(repositoryMock.findOne(ID)).thenReturn(program);

        //WHEN
        service.deleteProgramById(ID);

        //THEN
        verify(repositoryMock).findOne(ID);
        verify(repositoryMock).delete(ID);
    }
}