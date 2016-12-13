package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.frontend.component.ModuleWithTopics;
import com.epam.rd.frontend.service.TopicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.PUT;

@RunWith(MockitoJUnitRunner.class)
public class ModuleServiceImplTest {
    private static final Long ID = 1L;
    private List<ModuleDto> modules;
    private ModuleDto module;
    private List<TopicDto> topics;
    private TopicDto topic;
    private List<ModuleWithTopics> moduleWithTopicsList;
    private ModuleWithTopics moduleWithTopics;

    @Mock
    private RestOperations restOperationsMock;

    @Mock
    private TopicService topicServiceMock;

    @InjectMocks
    private ModuleServiceImpl moduleService;

    @Before
    public void set_up() {
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
    }

    @Test
    public void should_return_module_by_id() {
        //GIVEN
        when(restOperationsMock.getForObject(anyString(), eq(ModuleDto.class), anyLong())).thenReturn(module);

        //WHEN
        ModuleDto moduleDto = moduleService.getModuleById(ID);

        //THEN
        verify(restOperationsMock).getForObject(anyString(), eq(ModuleDto.class), anyLong());
        verifyNoMoreInteractions(restOperationsMock);
        assertEquals(module, moduleDto);
    }

    @Test
    public void should_return_topic_list_by_module_id() {
        //GIVEN
        ResponseEntity<List<ModuleDto>> modulesResponseEntity = new ResponseEntity<>(modules, HttpStatus.OK);
        when(restOperationsMock.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<ModuleDto>>() {
                }),
                anyLong()
        )).thenReturn(modulesResponseEntity);

        //WHEN
        List<ModuleDto> moduleDtoList = moduleService.getModulesByProgramId(ID);

        //THEN
        verify(restOperationsMock).exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<ModuleDto>>() {
                }),
                anyLong());
        verifyNoMoreInteractions(restOperationsMock);
        assertEquals(modules, moduleDtoList);
    }

    @Test
    public void should_return_module_with_topics_by_program_id() {
        //GIVEN
        when(topicServiceMock.getTopicsByModuleId(ID)).thenReturn(topics);
        ResponseEntity<List<ModuleDto>> modulesResponseEntity = new ResponseEntity<>(modules, HttpStatus.OK);
        when(restOperationsMock.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<ModuleDto>>() {
                }),
                eq(ID)
        )).thenReturn(modulesResponseEntity);

        //WHEN
        List<ModuleWithTopics> list = moduleService.getModulesWithTopics(ID);

        //THEN
        verify(restOperationsMock).exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<ModuleDto>>() {
                }),
                eq(ID));
        verifyNoMoreInteractions(restOperationsMock);
        assertEquals(moduleWithTopicsList, list);

    }

    @Test
    public void should_return_module_after_update() {
        //GIVEN
        String urlTemplate = "/module/{id}";
        HttpEntity<ModuleDto> httpEntity = new HttpEntity<>(module);
        ResponseEntity<ModuleDto> responseEntity = ResponseEntity.ok(module);
        when(restOperationsMock.exchange(eq(urlTemplate),
                eq(PUT),
                eq(httpEntity),
                eq(ModuleDto.class),
                eq(module.getId())))
                .thenReturn(responseEntity);
        //WHEN
        ModuleDto updateModule = moduleService.updateModule(module);
        //THEN
        assertSame(module, updateModule);
        verify(restOperationsMock, times(1)).exchange(eq(urlTemplate),
                eq(PUT),
                eq(httpEntity),
                eq(ModuleDto.class),
                eq(module.getId()));
        verifyNoMoreInteractions(restOperationsMock);
    }
}