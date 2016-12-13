package com.epam.rd.frontend.converter;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.frontend.model.ProgramInfo;
import com.epam.rd.frontend.model.ProgramTitles;
import com.epam.rd.frontend.model.TopicInfo;
import com.epam.rd.frontend.service.info.TopicInfoFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProgramInfoConverterTest {
    @Mock
    private TopicInfoFacade topicInfoFacade;

    private ProgramInfoConverter programInfoConverter;

    @Before
    public void set_up() {
        programInfoConverter = new ProgramInfoConverter(topicInfoFacade);
    }

    @Test
    public void convert_program_dto_list_to_program_title() {
        //GIVEN
        ProgramDto programDto1 = new ProgramDto();
        ProgramDto programDto2 = new ProgramDto();
        programDto1.setId(1L);
        programDto2.setId(2L);
        programDto1.setTitle("Title 1");
        programDto2.setTitle("Title 2");
        List<ProgramDto> programDtoList = new ArrayList<>();
        programDtoList.add(programDto1);
        programDtoList.add(programDto2);
        //WHEN
        ProgramTitles programTitles = programInfoConverter.convert(programDtoList);
        //THEN
        assertEquals(2, programTitles.getTitles().size());
        assertEquals("Title 1", programTitles.getTitles().get(1L));
        assertEquals("Title 2", programTitles.getTitles().get(2L));
    }

    @Test
    public void convert_program_dto_and_module_dto_list_to_program_info() {
        //GIVEN
        ProgramDto programDto = new ProgramDto();
        programDto.setId(1L);
        programDto.setTitle("Title 1");
        programDto.setStartDate(LocalDate.of(2014, 10, 1));
        programDto.setEndDate(LocalDate.of(2016, 10, 1));
        ModuleDto moduleDto1 = new ModuleDto();
        ModuleDto moduleDto2 = new ModuleDto();
        moduleDto1.setId(1L);
        moduleDto2.setId(2L);
        moduleDto1.setTitle("Title 1");
        moduleDto2.setTitle("Title 2");
        List<ModuleDto> moduleDtoList = new ArrayList<>();
        moduleDtoList.add(moduleDto1);
        moduleDtoList.add(moduleDto2);

        List<TopicInfo> topicInfoList = new ArrayList<>();
        topicInfoList.add(new TopicInfo(1L, "Topic #1", "Lecture #1", "Practical Task #1"));
        when(topicInfoFacade.getTopicInfoListByModuleId(1L)).thenReturn(topicInfoList);
        //WHEN
        ProgramInfo programInfo = programInfoConverter.convert(programDto, moduleDtoList);
        //THEN
        assertEquals("Title 1", programInfo.getTitle());
        assertEquals(LocalDate.of(2014, 10, 1), programInfo.getStart());
        assertEquals(LocalDate.of(2016, 10, 1), programInfo.getEnd());
        assertEquals(2, programInfo.getModules().size());
        assertEquals("Title 1", programInfo.getModules().get(1L));
        assertEquals("Title 2", programInfo.getModules().get(2L));
        assertEquals(1, programInfo.getTopicsByModuleId().size());
        verify(topicInfoFacade, times(1)).getTopicInfoListByModuleId(1L);
        verifyNoMoreInteractions(topicInfoFacade);
    }

}