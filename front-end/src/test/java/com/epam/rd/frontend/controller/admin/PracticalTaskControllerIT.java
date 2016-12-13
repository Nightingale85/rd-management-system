package com.epam.rd.frontend.controller.admin;

import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;
import com.epam.rd.frontend.config.SpringConfig;
import com.epam.rd.frontend.config.WebConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, WebConfig.class})
@WebAppConfiguration
public class PracticalTaskControllerIT {
    @Value("${url.domain}")
    private String domainUrl;

    private MockRestServiceServer mockRestServer;
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void set_up() {
        mockRestServer = MockRestServiceServer.createServer(restTemplate);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_return_html_page_for_create_practical_task() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/task/add";
        Long topicId = 10L;
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate).param("topicId", topicId.toString()));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("admin/new_task"))
                .andExpect(model().attribute("task", hasProperty("topicId", equalTo(topicId))));
    }

    @Test
    @WithMockUser(roles = {"LECTURER"})
    public void should_return_practical_task_id_after_create_new_practical_task() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/task/add";
        Long id = 10L;
        PracticalTaskDtoCreate dtoCreate = new PracticalTaskDtoCreate();
        dtoCreate.setTopicId(id);
        PracticalTaskDto taskDto = new PracticalTaskDto();
        taskDto.setId(id);
        taskDto.setTopicId(id);
        String taskJson = mapper.writeValueAsString(taskDto);
        mockRestServer.expect(requestTo(domainUrl + "/task"))
                .andExpect(method(POST))
                .andRespond(withSuccess(taskJson, APPLICATION_JSON_UTF8));
        //WHEN
        ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(APPLICATION_FORM_URLENCODED)
                .sessionAttr("task", dtoCreate));
        //THEN
        resultActions.andExpect(status().is3xxRedirection());

    }

    @Test
    @WithMockUser(roles = {"LECTURER"})
    public void should_return_html_page_for_edit_practical_task() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/task/edit";
        Long id = 1L;
        PracticalTaskDto taskDto = new PracticalTaskDto();
        taskDto.setId(id);
        taskDto.setTopicId(id);
        String taskJson = mapper.writeValueAsString(taskDto);
        mockRestServer.expect(requestTo(domainUrl + "/topic/1/task"))
                .andExpect(method(GET))
                .andRespond(withSuccess(taskJson, APPLICATION_JSON_UTF8));
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate).param("topicId", id.toString()));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("admin/edit_task"))
                .andExpect(model().attribute("task", hasProperty("id", equalTo(id))))
                .andExpect(model().attribute("task", hasProperty("topicId", equalTo(id))));
    }

    @Test
    @WithMockUser(roles = {"LECTURER"})
    public void should_return_update_practical_task_dto() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/task/edit";
        Long id = 1L;
        String title = "New Title";
        PracticalTaskDto taskDto = new PracticalTaskDto();
        taskDto.setId(id);
        taskDto.setTitle(title);
        taskDto.setTopicId(id);
        ObjectMapper mapper = new ObjectMapper();
        String taskJson = mapper.writeValueAsString(taskDto);
        mockRestServer.expect(requestTo(domainUrl + "/task/"))
                .andExpect(method(PUT))
                .andRespond(withSuccess(taskJson, APPLICATION_JSON_UTF8));
        //WHEN
        ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(APPLICATION_FORM_URLENCODED)
                .sessionAttr("task", taskDto));
        //THEN
        resultActions.andExpect(status().is3xxRedirection());

    }

}