package com.epam.rd.frontend.controller.info;

import com.epam.rd.dto.ProgramDto;
import com.epam.rd.frontend.config.SpringConfig;
import com.epam.rd.frontend.config.WebConfig;
import com.epam.rd.frontend.model.ProgramInfo;
import com.epam.rd.frontend.model.TopicInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.rd.frontend.TempDataFactory.*;
import static java.time.LocalDate.now;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, WebConfig.class})
@WebAppConfiguration
public class ProgramInfoControllerIT {
    @Value("${url.domain}")
    private String domainUrl;

    private MockRestServiceServer mockRestServer;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

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
    public void should_return_html_page_dashboard_info() throws Exception {
        //GIVEN
        List<ProgramDto> programDtoList = new ArrayList<>();
        programDtoList.add(getProgramDto(1L));
        programDtoList.add(getProgramDto(2L));
        prepareMockRestServer(mapper.writeValueAsString(programDtoList), "/programs");
        String urlTemplate = "/info/dashboard";
        Map<Long, String> titles = new HashMap<>();
        titles.put(1L, "Program #1");
        titles.put(2L, "Program #2");
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("info/dashboard_info"))
                     .andExpect(model().attribute("programs", hasProperty("titles", equalTo(titles))));
    }

    @Test
    @Ignore("Should create new program_info.html for model com.epam.rd.frontend.model.ProgramInfo")
    public void should_return_html_page_program_info() throws Exception {
        //GIVEN
        String urlTemplate = "/info/program";
        long id = 1L;
        prepareMockRestServer(mapper.writeValueAsString(getProgramDto(id, singletonList(id))), "/program/1");
        prepareMockRestServer(mapper.writeValueAsString(singletonList(getModuleDto(id, id, singletonList(id)))),
                              "/program/1/modules");
        prepareMockRestServer(mapper.writeValueAsString(singletonList(getTopicDto(id, id, id, id))),
                              "/module/1/topics");
        prepareMockRestServer(mapper.writeValueAsString(getLectureDto(id, id)), "/topic/1/lecture");
        prepareMockRestServer(mapper.writeValueAsString(getPracticalTaskDto(id, id)), "/topic/1/task");
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate).param("programId", "1"));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("info/program_info"))
                     .andExpect(model().attribute("program", hasProperty("title", equalTo("Program #1"))))
                     .andExpect(model().attribute("program", hasProperty("start", notNullValue())))
                     .andExpect(model().attribute("program", hasProperty("end", notNullValue())))
                     .andExpect(model().attribute("program", hasProperty("modules", notNullValue())))
                     .andExpect(model().attribute("program", hasProperty("topicsByModuleId", notNullValue())));
    }

    @Test
    @Ignore("Should create new program_info.html for model com.epam.rd.frontend.model.ProgramInfo")
    public void should_return_html_page_program_info_with_new_topics_info() throws Exception {
        //GIVEN
        String urlTemplate = "/info/program/module";
        long id = 2L;
        prepareMockRestServer(mapper.writeValueAsString(singletonList(getTopicDto(id, id, id, id))),
                              "/module/2/topics");
        prepareMockRestServer(mapper.writeValueAsString(getLectureDto(id, id)), "/topic/2/lecture");
        prepareMockRestServer(mapper.writeValueAsString(getPracticalTaskDto(id, id)), "/topic/2/task");
        Map<Long, String> modules = new HashMap<>();
        modules.put(1L, "Module #1");
        modules.put(2L, "Module #2");
        TopicInfo topicInfoForModuleOne = new TopicInfo(id, "Topic #1", "Lecture #1", "Practical Task #1");
        ProgramInfo programInfo =
                new ProgramInfo(1L, "Program #1", now(), now(), modules, singletonList(topicInfoForModuleOne));
        TopicInfo topicInfoForModuleTwo = new TopicInfo(id, "Topic #2", "Lecture #2", "Practical Task #2");
        //WHEN
        ResultActions resultActions = mockMvc
                .perform(get(urlTemplate).param("moduleId", "2").requestAttr("program", programInfo));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("info/program_info"))
                     .andExpect(model().attribute("program", hasProperty("title", equalTo("Program #1"))))
                     .andExpect(model().attribute("program", hasProperty("start", equalTo(programInfo.getStart()))))
                     .andExpect(model().attribute("program", hasProperty("end", equalTo(programInfo.getEnd()))))
                     .andExpect(model().attribute("program", hasProperty("modules", equalTo(modules))))
                     .andExpect(model()
                                        .attribute("program",
                                                   hasProperty("topicsByModuleId",
                                                               equalTo(singletonList(topicInfoForModuleTwo)))));
    }

    @SuppressWarnings("checkstyle:methodname")
    private void prepareMockRestServer(String json, String restUrl) throws JsonProcessingException {
        mockRestServer.expect(requestTo(domainUrl + restUrl))
                      .andExpect(method(GET))
                      .andRespond(withSuccess(json, APPLICATION_JSON_UTF8));
    }
}