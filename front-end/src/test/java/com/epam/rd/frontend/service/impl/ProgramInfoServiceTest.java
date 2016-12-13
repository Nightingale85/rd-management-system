package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.frontend.component.ProgramInfo;
import com.epam.rd.frontend.service.ModuleService;
import com.epam.rd.frontend.service.ProgramService;
import com.epam.rd.frontend.service.TopicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProgramInfoServiceTest {
    private static final Long ID = 1L;
    private List<ModuleDto> modules;
    private ModuleDto module;
    private ProgramDto program;
    private List<TopicDto> topics;
    private TopicDto topic;

    @Mock
    private ProgramService programServiceMock;

    @Mock
    private ModuleService moduleServiceMock;

    @Mock
    private TopicService topicServiceMock;

    @InjectMocks
    private ProgramInfoServiceImpl programInfoService;

    @Before
    public void set_up() {
        modules = new ArrayList<>();
        module = new ModuleDto();
        module.setTitle("moduleTitle");
        module.setDescription("moduleDescription");
        module.setId(ID);
        module.setProgramId(ID);
        modules.add(module);

        program = new ProgramDto();
        program.setTitle("programTitle");
        program.setDescription("programDescription");
        program.setId(ID);

        topics = new ArrayList<>();
        topic = new TopicDto();
        topic.setTitle("topicTitle");
        topic.setDescription("topicDescription");
        topic.setId(ID);
        topic.setModuleId(ID);
        topics.add(topic);
    }

    @Test
    public void should_return_program_info_by_program_id() {
        //GIVEN
        when(programServiceMock.getProgramById(anyLong())).thenReturn(program);
        when(moduleServiceMock.getModulesByProgramId(anyLong())).thenReturn(modules);
        when(topicServiceMock.getTopicsByModuleId(anyLong())).thenReturn(topics);

        //WHEN
        ProgramInfo programInfo = programInfoService.getProgramInfoByProgramId(ID);

        //THEN
        assertEquals(new ProgramInfo(program.getTitle(), modules, module, topics), programInfo);
    }

    @Test
    public void should_return_program_info_by_module_id() {
        //GIVEN
        when(programServiceMock.getProgramById(anyLong())).thenReturn(program);
        when(moduleServiceMock.getModuleById(anyLong())).thenReturn(module);
        when(moduleServiceMock.getModulesByProgramId(anyLong())).thenReturn(modules);
        when(topicServiceMock.getTopicsByModuleId(anyLong())).thenReturn(topics);

        //WHEN
        ProgramInfo programInfo = programInfoService.getProgramInfoByModuleId(ID);

        //THEN
        assertEquals(new ProgramInfo(program.getTitle(), modules, module, topics), programInfo);
    }
}
