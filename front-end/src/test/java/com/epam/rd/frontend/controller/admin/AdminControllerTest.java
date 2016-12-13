package com.epam.rd.frontend.controller.admin;

import com.epam.rd.dto.*;
import com.epam.rd.frontend.component.ModuleWithTopics;
import com.epam.rd.frontend.controller.AdminController;
import com.epam.rd.frontend.service.ModuleService;
import com.epam.rd.frontend.service.ProgramService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
    private MockMvc mockMvc;
    private static final Long ID = 1L;
    private List<ModuleDto> modules;
    private ModuleDto module;
    private List<TopicDto> topics;
    private TopicDto topic;
    private List<ModuleWithTopics> moduleWithTopicsList;
    private ModuleWithTopics moduleWithTopics;
    private ProgramDto programDto;
    private ProgramDtoCreate programDtoCreate;

    @Mock
    private ProgramService programServiceMock;
    @Mock
    private ModuleService moduleServiceMock;

    @Before
    public void set_up() {
        AdminController controller = new AdminController(moduleServiceMock, programServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        modules = new ArrayList<>();
        module = new ModuleDto();
        module.setTitle("moduleTitle");
        module.setDescription("moduleDescription");
        module.setId(ID);
        module.setProgramId(ID);
        modules.add(module);

        topics = new ArrayList<>();
        topic = new TopicDto();
        topic.setTitle("topicTitle");
        topic.setDescription("topicDescription");
        topic.setId(ID);
        topic.setModuleId(ID);
        topics.add(topic);

        moduleWithTopics = new ModuleWithTopics();
        moduleWithTopics.setModuleDto(module);
        moduleWithTopics.setTopicDtoList(topics);
        moduleWithTopicsList = new ArrayList<>();
        moduleWithTopicsList.add(moduleWithTopics);

        programDto = new ProgramDto();
        programDto.setId(ID);
        programDto.setTitle("Title");
        programDto.setDescription("Description");

        programDtoCreate = new ProgramDtoCreate();
        programDtoCreate.setTitle("Title");
        programDtoCreate.setDescription("Description");
    }

    @Test
    public void should_redirect_after_add_new_program() throws Exception {
        //GIVEN
        when(programServiceMock.addProgram(eq(programDtoCreate))).thenReturn(ID);
        //WHEN
        final String urlTemplate = "/admin/new_program";
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate));

        //THEN
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void should_get_program_by_id_with_module_and_list_topics() throws Exception {
        //GIVEN
        final String urlTemplate = "/admin/edit/program/1";
        when(programServiceMock.getProgramById(ID)).thenReturn(programDto);
        when(moduleServiceMock.getModulesWithTopics(ID)).thenReturn(moduleWithTopicsList);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));

        //THEN
        verify(programServiceMock, times(1)).getProgramById(eq(ID));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("moduleDtoCreate"))
                .andExpect(model().attribute("moduleWithTopics", moduleWithTopicsList))
                .andExpect(model().attribute("programDto", programDto));
    }

    @Test
    public void should_redirect_after_update_program() throws Exception {
        //GIVEN
        final String urlTemplate = "/admin/update_program";
        when(programServiceMock.updateProgram(programDto)).thenReturn(programDto);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate));

        //THEN
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void should_redirect_after_create_module() throws Exception {
        //GIVEN
        final String urlTemplate = "/admin/add_module";
        when(moduleServiceMock.addModule(any(ModuleDtoCreate.class))).thenReturn(ID);

        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate));

        //THEN
        resultActions.andExpect(status().is3xxRedirection());
        verify(moduleServiceMock, times(1)).addModule(any(ModuleDtoCreate.class));
    }
    @Test
    public void should_redirect_after_delete_module() throws Exception {
        //GIVEN
        final String urlTemplate = "/admin/delete_module/1";
        doNothing().when(moduleServiceMock).deleteModuleById(ID);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate));

        //THEN
        verify(moduleServiceMock, times(1)).deleteModuleById(ID);
        resultActions.andExpect(status().is3xxRedirection());
    }
}


