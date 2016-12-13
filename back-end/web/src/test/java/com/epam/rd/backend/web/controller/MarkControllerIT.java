package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.service.MarkService;
import com.epam.rd.backend.web.config.WebConfig;
import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.MarkDtoCreate;
import com.epam.rd.dto.RoleDto;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, SpringCoreConfig.class})
@WebAppConfiguration
@TestPropertySource(value = "classpath:app-config-test.properties")
@Transactional
public class MarkControllerIT {
    private static final int MARK = 10;
    private static final long ID = 1L;
    private final String MENTEE_EMAIL = "mentee@email.com";
    private final String LECTURER_EMAIL = "lecturer@email.com";
    private final String FEEDBACK = "feedback";
    private MockMvc mockMvc;
    private MarkDtoCreate markDtoCreate;
    private MarkDto markDto;

    @Autowired
    private MarkService markService;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void set_up() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        markDtoCreate = new MarkDtoCreate();
        markDtoCreate.setPracticalTaskId(ID);
        markDtoCreate.setMenteeEmail(MENTEE_EMAIL);
        markDtoCreate.setEstimatorEmail(LECTURER_EMAIL);
        markDtoCreate.setEstimatorRole(RoleDto.LECTURER);
        markDtoCreate.setMark(MARK);
        markDtoCreate.setFeedback(FEEDBACK);
        markDto = markService.createMark(markDtoCreate);
    }

    @Test
    public void should_return_mark_by_id() throws Exception {
        //GIVEN
        String urlTemplate = "/mark/" + markDto.getId();
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void should_return_error_if_mark_not_found() throws Exception {
        //GIVEN
        String urlTemplate = "/mark/" + markDto.getId() + 1;
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().is4xxClientError());
    }


    @Test
    public void should_add_mark() throws Exception {
        //GIVEN
        String urlTemplate = "/mark";
        ObjectMapper mapper = new ObjectMapper();
        String markJson = mapper.writeValueAsString(markDtoCreate);
        mockMvc.perform(post(urlTemplate).contentType(APPLICATION_JSON_UTF8)
                .content(markJson));

        //WHEN
        ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(APPLICATION_JSON_UTF8)
                .content(markJson));
        //THEN
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void should_update_mark_by_id() throws Exception {
        //GIVEN
        String urlTemplate = "/mark/" + markDto.getId();
        markDtoCreate.setFeedback("new feedback");
        ObjectMapper mapper = new ObjectMapper();
        String markJson = mapper.writeValueAsString(markDtoCreate);
        //WHEN
        ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(APPLICATION_JSON_UTF8)
                .content(markJson));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void should_return_error_if_mark_for_update_not_found() throws Exception {
        //GIVEN
        String urlTemplate = "/mark/" + markDto.getId() + 1;
        markDtoCreate.setFeedback("new feedback");
        ObjectMapper mapper = new ObjectMapper();
        String markJson = mapper.writeValueAsString(markDtoCreate);
        //WHEN
        ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(APPLICATION_JSON_UTF8)
                .content(markJson));
        //THEN
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void should_not_update_without_dto() throws Exception {
        //GIVEN
        String urlTemplate = "/mark/" + markDto.getId();
        ObjectMapper mapper = new ObjectMapper();
        String markJson = mapper.writeValueAsString(null);
        //WHEN
        ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(APPLICATION_JSON_UTF8)
                .content(markJson));
        //THEN
        resultActions.andExpect(status().is5xxServerError());
    }

    @Test
    public void should_not_update_without_role() throws Exception {
        //GIVEN
        String urlTemplate = "/mark/" + markDto.getId();
        ObjectMapper mapper = new ObjectMapper();
        MarkDtoCreate newDto = new MarkDtoCreate();
        newDto.setFeedback("test");
        String markJson = mapper.writeValueAsString(newDto);
        //WHEN
        ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(APPLICATION_JSON_UTF8)
                .content(markJson));
        //THEN
        resultActions.andExpect(status().is5xxServerError());
    }

    @Test
    public void should_not_update_without_practical_task_id() throws Exception {
        //GIVEN
        String urlTemplate = "/mark/" + markDto.getId();
        ObjectMapper mapper = new ObjectMapper();
        MarkDtoCreate newDto = new MarkDtoCreate();
        newDto.setFeedback("test");
        newDto.setMenteeEmail("mentee@email.com");
        newDto.setEstimatorEmail("mentor@email.com");
        newDto.setEstimatorRole(RoleDto.LECTURER);
        String markJson = mapper.writeValueAsString(newDto);
        //WHEN
        ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(APPLICATION_JSON_UTF8)
                .content(markJson));
        //THEN
        resultActions.andExpect(status().is5xxServerError());
    }

    @Test
    public void should_delete_mark_by_id() throws Exception {
        //GIVEN
        String urlTemplate = "/mark/" + markDto.getId();
        //WHEN
        ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void should_return_error_if_mark_not_found_by_id() throws Exception {
        //GIVEN
        String urlTemplate = "/mark/" + markDto.getId() + 1;
        //WHEN
        ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void should_return_list_mark_dto_by_mentee_email() throws Exception {
        //GIVEN
        String urlTemplate = "/marks/" + markDto.getMenteeEmail();
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].practicalTaskId", is(1)))
                .andExpect(jsonPath("$[0].menteeEmail", is(MENTEE_EMAIL)))
                .andExpect(jsonPath("$[0].estimatorEmail", is(LECTURER_EMAIL)))
                .andExpect(jsonPath("$[0].estimatorRole", is(RoleDto.LECTURER.toString())))
                .andExpect(jsonPath("$[0].mark", is(MARK)))
                .andExpect(jsonPath("$[0].feedback", is(FEEDBACK)));
    }

    @Test
    public void should_return_list_mark_dto_by_estimator_email_and_task_id() throws Exception {
        //GIVEN
        String urlTemplate = "/task/{id}/marks/{email}";
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate,
                                                          markDto.getPracticalTaskId(),
                                                          markDto.getEstimatorEmail()));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].practicalTaskId", is(1)))
                .andExpect(jsonPath("$[0].menteeEmail", is(MENTEE_EMAIL)))
                .andExpect(jsonPath("$[0].estimatorEmail", is(LECTURER_EMAIL)))
                .andExpect(jsonPath("$[0].estimatorRole", is(RoleDto.LECTURER.toString())))
                .andExpect(jsonPath("$[0].mark", is(MARK)))
                .andExpect(jsonPath("$[0].feedback", is(FEEDBACK)));
    }

    @Test
    public void should_not_return_list_mark_dto_by_estimator_email_and_task_id() throws Exception {
        //GIVEN
        String urlTemplate = "/task/{id}/marks/{email}";
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate,
                markDto.getPracticalTaskId(),
                MENTEE_EMAIL));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }
}
