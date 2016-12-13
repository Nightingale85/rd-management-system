package com.epam.rd.frontend.service.info;

import com.epam.rd.dto.ProgramDto;
import com.epam.rd.frontend.converter.ProgramInfoConverter;
import com.epam.rd.frontend.model.ProgramTitles;
import com.epam.rd.frontend.service.ModuleService;
import com.epam.rd.frontend.service.ProgramService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProgramInfoFacadeTest {
    @Mock
    private ProgramService programService;

    @Mock
    private ModuleService moduleService;

    @Mock
    private ProgramInfoConverter converter;

    private ProgramInfoFacade programInfoFacade;

    @Before
    public void set_up() {
        programInfoFacade = new ProgramInfoFacade(programService, moduleService, converter);
    }

    @Test
    public void should_return_program_titles() {
        //GIVEN
        ProgramTitles expectedProgramTitles = getProgramTitles();
        int count = 3;
        List<ProgramDto> programDtoList = getProgramDtoList(count);
        when(programService.getAllPrograms()).thenReturn(programDtoList);
        when(converter.convert(programDtoList)).thenReturn(expectedProgramTitles);
        //WHEN
        ProgramTitles actualProgramTitles = programInfoFacade.getProgramsInfo();
        //THEN
        assertEquals(expectedProgramTitles, actualProgramTitles);
        verify(programService, times(1)).getAllPrograms();
        verifyNoMoreInteractions(programService);
    }

    @SuppressWarnings("checkstyle:methodname")
    private List<ProgramDto> getProgramDtoList(int count) {
        List<ProgramDto> programDtoList = new ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            ProgramDto programDto = new ProgramDto();
            long id = index + 1L;
            programDto.setId(id);
            programDto.setTitle(String.format("Program #%d", id));
            programDtoList.add(programDto);
        }
        return programDtoList;
    }

    @SuppressWarnings("checkstyle:methodname")
    private ProgramTitles getProgramTitles() {
        Map<Long, String> programInfoList = new HashMap<>();
        programInfoList.put(1L, "Program #1");
        programInfoList.put(2L, "Program #2");
        programInfoList.put(3L, "Program #3");
        return new ProgramTitles(programInfoList);
    }
}