package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.Program;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * @author Oleksii_Ushakov
 */
public class ProgramDtoConverterTest {
    private static final LocalDate DATE_END = LocalDate.of(2000, 2, 2);
    private static final LocalDate DATE_START = LocalDate.of(2000, 2, 2);
    private static final String TEST_NAME = "Test name";
    private static final Long ID = 999L;

    private static ProgramDtoConverter converter = new ProgramDtoConverter();

    @Test
    public void should_convert_program_to_program_dto() {
        //GIVEN
        Program program = new Program();
        program.setDateStart(DATE_START);
        program.setDateEnd(DATE_END);
        program.setId(ID);
        program.setName(TEST_NAME);

        //WHEN
        ProgramDto programDto = converter.convert(program);

        //THEN
        assertProgramEquals(programDto, program);
    }

    @Test
    public void should_convert_program_dto_to_program() {
        ProgramDto programDto = new ProgramDto();
        programDto.setStartDate(DATE_START);
        programDto.setEndDate(DATE_END);
        programDto.setId(ID);
        programDto.setTitle(TEST_NAME);

        //WHEN
        Program program = converter.convert(programDto);

        //THEN
        assertProgramEquals(programDto, program);
    }

    @Test
    public void should_convert_program_dto_create_to_program() {
        ProgramDtoCreate programDtoCreate = new ProgramDtoCreate();
        programDtoCreate.setStartDate(DATE_START);
        programDtoCreate.setEndDate(DATE_END);
        programDtoCreate.setTitle(TEST_NAME);

        //WHEN
        Program program = converter.convert(programDtoCreate);

        //THEN
        assertProgramEquals(programDtoCreate, program);
    }

    @SuppressWarnings("checkstyle:methodname")
    private void assertProgramEquals(ProgramDto programDto, Program program) {
        assertEquals(programDto.getId(), program.getId());
        assertEquals(programDto.getTitle(), program.getName());
        assertEquals(programDto.getEndDate(), program.getDateEnd());
        assertEquals(programDto.getStartDate(), program.getDateStart());
    }

    @SuppressWarnings("checkstyle:methodname")
    private void assertProgramEquals(ProgramDtoCreate programDtoCreate, Program program) {
        assertEquals(program.getName(), programDtoCreate.getTitle());
        assertEquals(program.getDateEnd(), programDtoCreate.getEndDate());
        assertEquals(program.getDateStart(), programDtoCreate.getStartDate());
    }
}