package com.epam.rd.frontend.controller.admin;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.LectureDtoCreate;
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
public class LectureControllerIT {
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
    public void should_return_html_page_for_create_lecture() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/lecture/add";
        Long topicId = 10L;
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate).param("topicId", topicId.toString()));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("admin/new_lecture"))
                .andExpect(model().attribute("lecture",
                        hasProperty("topicId", equalTo(topicId))));
    }

    @Test
    @WithMockUser(roles = {"LECTURER"})
    public void should_return_lecture_id_after_create_new_lecture() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/lecture/add";
        Long id = 10L;
        LectureDtoCreate dtoCreate = new LectureDtoCreate();
        dtoCreate.setTopicId(id);
        LectureDto lectureDto = new LectureDto();
        lectureDto.setId(id);
        lectureDto.setTopicId(id);
        String lectureJson = mapper.writeValueAsString(lectureDto);
        mockRestServer.expect(requestTo(domainUrl + "/lecture"))
                .andExpect(method(POST))
                .andRespond(withSuccess(lectureJson, APPLICATION_JSON_UTF8));
        //WHEN
        ResultActions resultActions = mockMvc
                .perform(post(urlTemplate).contentType(APPLICATION_FORM_URLENCODED)
                        .sessionAttr("lecture", dtoCreate));
        //THEN
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void should_return_html_page_for_edit_lecture() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/lecture/edit";
        Long id = 1L;
        LectureDto lectureDto = new LectureDto();
        lectureDto.setId(id);
        lectureDto.setTopicId(id);
        String lectureJson = mapper.writeValueAsString(lectureDto);
        mockRestServer.expect(requestTo(domainUrl + "/topic/1/lecture"))
                .andExpect(method(GET))
                .andRespond(withSuccess(lectureJson, APPLICATION_JSON_UTF8));
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate).param("topicId",
                id.toString()));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("admin/edit_lecture"))
                .andExpect(model().attribute("lecture", hasProperty("id", equalTo(id))))
                .andExpect(model().attribute("lecture", hasProperty("topicId", equalTo(id))));
    }

    @Test
    @WithMockUser(roles = {"LECTURER"})
    public void should_return_update_lecture_dto() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/lecture/edit";
        Long id = 1L;
        String title = "New Title";
        LectureDto lectureDto = new LectureDto();
        lectureDto.setId(id);
        lectureDto.setTitle(title);
        lectureDto.setTopicId(id);
        String lectureJson = mapper.writeValueAsString(lectureDto);
        mockRestServer.expect(requestTo(domainUrl + "/lecture/"))
                .andExpect(method(PUT))
                .andRespond(withSuccess(lectureJson, APPLICATION_JSON_UTF8));
        //WHEN
        ResultActions resultActions = mockMvc
                .perform(post(urlTemplate).contentType(APPLICATION_FORM_URLENCODED)
                        .sessionAttr("lecture", lectureDto));
        //THEN
        resultActions.andExpect(status().is3xxRedirection());
    }

}