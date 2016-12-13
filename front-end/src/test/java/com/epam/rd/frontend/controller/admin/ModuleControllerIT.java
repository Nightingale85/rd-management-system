package com.epam.rd.frontend.controller.admin;


import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;
import com.epam.rd.frontend.TempDataFactory;
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

import java.util.ArrayList;
import java.util.List;

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
public class ModuleControllerIT {


    @Value("${url.domain}")
    private String domainUrl;

    private MockRestServiceServer mockRestServer;
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private ModuleDto moduleDto;
    private TopicDto topicDto;
    private List<TopicDto> topicDtos;
    private TopicDtoCreate topicDtoCreate;
    private Long id;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void set_up() {
        id = 1L;
        mockRestServer = MockRestServiceServer.createServer(restTemplate);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        moduleDto = TempDataFactory.getModuleDto(id);
        moduleDto.setId(id);
        moduleDto.setTitle("Title module");
        moduleDto.setDescription("Description module");

        topicDto = TempDataFactory.getTopicDto(id);
        topicDto.setModuleId(moduleDto.getId());

        topicDtos = new ArrayList<>();
        topicDtos.add(topicDto);

        topicDtoCreate = TempDataFactory.getTopicDtoCreate(id);
    }

    @Test
    public void should_return_page_for_edit_module() throws Exception {
        //GIVEN
        String moduleJson = mapper.writeValueAsString(moduleDto);
        String topicsJson = mapper.writeValueAsString(topicDtos);
        mockRestServer.expect(requestTo(domainUrl + "/module/" + moduleDto.getId()))
                .andExpect(method(GET))
                .andRespond(withSuccess(moduleJson, APPLICATION_JSON_UTF8));
        mockRestServer.expect(requestTo(domainUrl + "/module/" + moduleDto.getId() + "/topics"))
                .andExpect(method(GET))
                .andRespond(withSuccess(topicsJson, APPLICATION_JSON_UTF8));
        String urlTemplate = "/admin/module/" + moduleDto.getId();
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("admin/edit_module"))
                .andExpect(model().attribute("moduleDto", hasProperty("id", equalTo(moduleDto.getId()))))
                .andExpect(model().attribute("moduleDto", hasProperty("title", equalTo(moduleDto.getTitle()))))
                .andExpect(model().attribute("moduleDto", hasProperty("description",
                        equalTo(moduleDto.getDescription()))))
                .andExpect(model().attribute("topicDtos", topicDtos))
                .andExpect(model().attributeExists("topicDtoCreate"));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void should_return_update_module() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/module/edit";
        String moduleJson = mapper.writeValueAsString(moduleDto);
        mockRestServer.expect(requestTo(domainUrl + "/module/"))
                .andExpect(method(PUT))
                .andRespond(withSuccess(moduleJson, APPLICATION_JSON_UTF8));
        //WHEN
        ResultActions resultActions = mockMvc
                .perform(post(urlTemplate).contentType(APPLICATION_FORM_URLENCODED)
                        .sessionAttr("moduleDto", moduleDto));
        //THEN
        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("moduleDto"));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void should_return_topic_after_create_new_topic() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/topic/add";
        String topicJson = mapper.writeValueAsString(topicDto);
        mockRestServer.expect(requestTo(domainUrl + "/topic"))
                .andExpect(method(POST))
                .andRespond(withSuccess(topicJson, APPLICATION_JSON_UTF8));
        //WHEN
        ResultActions resultActions = mockMvc
                .perform(post(urlTemplate).contentType(APPLICATION_FORM_URLENCODED)
                        .sessionAttr("topicDtoCreate", topicDtoCreate)
                        .sessionAttr("moduleDto", moduleDto));
        //THEN
        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("moduleDto"))
                .andExpect(model().attributeExists("topicDtoCreate"));
    }

}
