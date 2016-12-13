package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.web.config.WebConfig;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;
import com.epam.rd.dto.TopicDtoCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, SpringCoreConfig.class})
@WebAppConfiguration
@TestPropertySource(value = "classpath:app-config-test.properties")
public class PracticalTaskControllerIT {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void set_up() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_list_of_practical_task() throws Exception {
        //GIVEN
        String urlTemplate = "/tasks";
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_practical_task_by_id() throws Exception {
        //GIVEN
        String urlTemplate = "/task/1";
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_practical_task_by_topic_id() throws Exception {
        //GIVEN
        String urlTemplate = "/topic/1/task";
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Transactional
    @Test
    @Ignore("Should prepare database before running test")
    public void should_add_practical_task() throws Exception {
        //GIVEN
        String urlTemplate = "/topic";
        TopicDtoCreate topicDtoCreate = new TopicDtoCreate();
        topicDtoCreate.setTitle("New Topic");
        ObjectMapper mapper = new ObjectMapper();
        String topicJson = mapper.writeValueAsString(topicDtoCreate);
        mockMvc.perform(post(urlTemplate).contentType(APPLICATION_JSON_UTF8)
                                         .content(topicJson));
        urlTemplate = "/task";
        PracticalTaskDtoCreate dtoCreate = new PracticalTaskDtoCreate();
        dtoCreate.setTitle("New Practical Task");
        dtoCreate.setTopicId(10L);
        String taskJson = mapper.writeValueAsString(dtoCreate);
        //WHEN
        ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(APPLICATION_JSON_UTF8)
                .content(taskJson));
        //THEN
        resultActions.andExpect(status().isCreated())
                     .andExpect(header().string("location", "http://localhost/task/10"));
    }

    @Transactional
    @Test
    @Ignore("Should prepare database before running test")
    public void should_update_practical_task_by_id() throws Exception {
        //GIVEN
        String urlTemplate = "/task/2";
        PracticalTaskDto dto = new PracticalTaskDto();
        dto.setId(1L);
        dto.setTitle("New Practical Task Title");
        dto.setTopicId(1L);
        ObjectMapper mapper = new ObjectMapper();
        String taskJson = mapper.writeValueAsString(dto);
        //WHEN
        ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(APPLICATION_JSON_UTF8)
                .content(taskJson));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Transactional
    @Test
    @Ignore("Should prepare database before running test")
    public void should_delete_practical_task_by_id() throws Exception {
        //GIVEN
        String urlTemplate = "/task/1";
        //WHEN
        ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }
}