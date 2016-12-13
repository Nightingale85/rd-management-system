package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.service.TopicService;
import com.epam.rd.backend.web.config.WebConfig;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, SpringCoreConfig.class})
@WebAppConfiguration
@TestPropertySource(value = "classpath:app-config-test.properties")
public class TopicControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private TopicService service;

    @Autowired
    private TopicController controller;

    //FIXME: use webcontext
    @Before
    public void set_up() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_all_topic() throws Exception {
        //GIVEN
        final String urlTemplate = "/topics";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("TopicName1")))
                .andExpect(jsonPath("$[0].description", is("LinkToDescription1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("TopicName2")))
                .andExpect(jsonPath("$[1].description", is("LinkToDescription2")));
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_topics_by_module_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/1/topics";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].title", is("TopicName2")))
                .andExpect(jsonPath("$[0].description", is("LinkToDescription2")));
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_topic_with_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/topic/1";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                     .andExpect(jsonPath("$.id", is(1)))
                     .andExpect(jsonPath("$.title", is("TopicName1")))
                     .andExpect(jsonPath("$.description", is("LinkToDescription1")));
    }

    @Test(expected = Exception.class)
    public void should_throw_exception_if_topic_id_absent() throws Exception {
        //GIVEN
        final String urlTemplate = "/topic/" + Integer.MAX_VALUE;
        //WHEN
        mockMvc.perform(get(urlTemplate));
        //THEN
        fail();
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void add_topic_to_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/topic";
        final TopicDtoCreate dtoCreate = new TopicDtoCreate();
        dtoCreate.setTitle("New Topic");
        dtoCreate.setDescription("new Link to Description");
        final ObjectMapper mapper = new ObjectMapper();
        final String topicJson = mapper.writeValueAsString(dtoCreate);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                                                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                                    .content(topicJson));
        //THEN
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void update_topic_in_database_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/topic/2";
        TopicDto dto = service.getTopicById(2L);
        dto.setTitle("new Topic Name");
        final ObjectMapper mapper = new ObjectMapper();
        final String topicJson = mapper.writeValueAsString(dto);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                                                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                                    .content(topicJson));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                     .andExpect(jsonPath("$.id", is(2)))
                     .andExpect(jsonPath("$.title", is("new Topic Name")))
                     .andExpect(jsonPath("$.description", is("LinkToDescription2")));
    }

    @Test(expected = Exception.class)
    public void try_update_not_existed_topic_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/topic/" + Integer.MAX_VALUE;
        final TopicDto dto = new TopicDto();
        final ObjectMapper mapper = new ObjectMapper();
        final String topicJson = mapper.writeValueAsString(dto);
        //WHEN
        mockMvc.perform(put(urlTemplate).contentType(MediaType.APPLICATION_JSON_UTF8)
                                        .content(topicJson));
        //THEN
        fail();
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void delete_topic_in_database_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/topic/2";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }

    @Test(expected = Exception.class)
    public void try_delete_topic_not_existed_topic_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/topic/" + Integer.MAX_VALUE;
        //WHEN
        mockMvc.perform(delete(urlTemplate));
        //THEN
        fail();
    }

}