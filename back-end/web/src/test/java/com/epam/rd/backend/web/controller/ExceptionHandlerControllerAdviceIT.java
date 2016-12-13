package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.model.PracticalTask;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.backend.core.repository.TopicRepository;
import com.epam.rd.backend.web.config.WebConfig;
import com.epam.rd.dto.PracticalTaskDtoCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, SpringCoreConfig.class})
@WebAppConfiguration
@TestPropertySource(value = "classpath:app-config-test.properties")
public class ExceptionHandlerControllerAdviceIT {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void before() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_handle_entity_does_not_exist_exception() throws Exception {
        //GIVEN
        final Long id = Long.MAX_VALUE;
        final String urlTemplate = "/program/" + id;
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.reason", is("Cannot find program by ID=" + id)))
                .andExpect(jsonPath("$.classException",
                        is("com.epam.rd.backend.core.exception.EntityDoesNotExistException")));
    }

    @Transactional
    @Test
    public void should_handle_entity_is_already_exist_exception() throws Exception {
        //GIVEN
        Topic topic = new Topic();
        PracticalTask task = new PracticalTask();
        topic.setPracticalTask(task);
        Topic savedTopic = topicRepository.save(topic);
        final Long topicId = savedTopic.getId();
        final Long practicalTaskId = savedTopic.getPracticalTask().getId();
        PracticalTaskDtoCreate taskDtoCreate = new PracticalTaskDtoCreate();
        taskDtoCreate.setTopicId(topicId);
        String taskJson = mapper.writeValueAsString(taskDtoCreate);
        final String urlTemplate = "/task/";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(APPLICATION_JSON_UTF8)
                .content(taskJson));
        //THEN
        resultActions.andExpect(status().isConflict())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.reason",
                        is("Topic with ID=" + topicId + " already contains practical task with ID=" + practicalTaskId)))
                .andExpect(jsonPath("$.classException",
                        is("com.epam.rd.backend.core.exception.EntityIsAlreadyExistException")));
    }

    @Test
    public void should_handle_exception() throws Exception {
        //GIVEN
        final String urlTemplate = "/module";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate).content(""));
//        THEN
        resultActions.andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.reason", is("Required request body is missing: " +
                        "public org.springframework.http.ResponseEntity<com.epam.rd.dto.ModuleDto> " +
                        "com.epam.rd.backend.web.controller.ModuleController.addModule" +
                        "(com.epam.rd.dto.ModuleDtoCreate," +
                        "org.springframework.web.util.UriComponentsBuilder)")))
                .andExpect(jsonPath("$.classException",
                        is("org.springframework.http.converter.HttpMessageNotReadableException")));
    }
}
