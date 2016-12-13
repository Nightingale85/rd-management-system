package com.epam.rd.frontend.controller;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.frontend.config.SpringConfig;
import com.epam.rd.frontend.config.WebConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, WebConfig.class})
@WebAppConfiguration
public class InfoProgramControllerIT {
    @Value("${url.domain}")
    private String domainUrl;

    private MockRestServiceServer mockRestServer;
    private MockMvc mockMvc;

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
    public void should_return_program_info_page_by_id() throws Exception {
        //GIVEN
        String urlTemplate = "/program/info/1";

        ProgramDto programDto = new ProgramDto();
        programDto.setId(1L);

        List<ModuleDto> moduleDtoList = new ArrayList<>();
        ModuleDto moduleDto = new ModuleDto();

        moduleDto.setId(1L);
        moduleDto.setProgramId(1L);
        moduleDtoList.add(moduleDto);

        List<TopicDto> topicDtoList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        String programDtoJson = mapper.writeValueAsString(programDto);
        String moduleDtoListJson = mapper.writeValueAsString(moduleDtoList);
        String topicDtoListJson = mapper.writeValueAsString(topicDtoList);

        mockRestServer.expect(requestTo(domainUrl + "/program/1"))
                .andExpect(method(GET))
                .andRespond(withSuccess(programDtoJson, APPLICATION_JSON_UTF8));

        mockRestServer.expect(requestTo(domainUrl + "/program/1/modules"))
                .andExpect(method(GET))
                .andRespond(withSuccess(moduleDtoListJson, APPLICATION_JSON_UTF8));

        mockRestServer.expect(requestTo(domainUrl + "/module/1/topics"))
                .andExpect(method(GET))
                .andRespond(withSuccess(topicDtoListJson, APPLICATION_JSON_UTF8));

        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate));

        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("info/program_info"))
                .andExpect(model().attribute("programInfo", hasProperty("modules", notNullValue())))
                .andExpect(model().attribute("programInfo", hasProperty("topics", notNullValue())))
                .andExpect(model().attribute("programInfo", hasProperty("currentModule", notNullValue())));
    }

    @Test
    public void should_return_program_info_page_by_module_id() throws Exception {
        //GIVEN
        String urlTemplate = "/program/info/module/1";

        ProgramDto programDto = new ProgramDto();
        programDto.setId(1L);

        List<ModuleDto> moduleDtoList = new ArrayList<>();
        ModuleDto moduleDto = new ModuleDto();

        moduleDto.setId(1L);
        moduleDto.setProgramId(1L);
        moduleDtoList.add(moduleDto);

        List<TopicDto> topicDtoList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        String programDtoJson = mapper.writeValueAsString(programDto);
        String moduleDtoJson = mapper.writeValueAsString(moduleDto);
        String moduleDtoListJson = mapper.writeValueAsString(moduleDtoList);
        String topicDtoListJson = mapper.writeValueAsString(topicDtoList);

        mockRestServer.expect(requestTo(domainUrl + "/module/1"))
                .andExpect(method(GET))
                .andRespond(withSuccess(moduleDtoJson, APPLICATION_JSON_UTF8));

        mockRestServer.expect(requestTo(domainUrl + "/program/1/modules"))
                .andExpect(method(GET))
                .andRespond(withSuccess(moduleDtoListJson, APPLICATION_JSON_UTF8));

        mockRestServer.expect(requestTo(domainUrl + "/program/1"))
                .andExpect(method(GET))
                .andRespond(withSuccess(programDtoJson, APPLICATION_JSON_UTF8));

        mockRestServer.expect(requestTo(domainUrl + "/module/1/topics"))
                .andExpect(method(GET))
                .andRespond(withSuccess(topicDtoListJson, APPLICATION_JSON_UTF8));

        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate));

        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("info/program_info"))
                .andExpect(model().attribute("programInfo", hasProperty("modules", notNullValue())))
                .andExpect(model().attribute("programInfo", hasProperty("topics", notNullValue())))
                .andExpect(model().attribute("programInfo", hasProperty("currentModule", notNullValue())));
    }
}