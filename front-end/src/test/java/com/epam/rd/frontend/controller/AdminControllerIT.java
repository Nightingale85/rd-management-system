package com.epam.rd.frontend.controller;

import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;
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

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
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
public class AdminControllerIT {
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
    public void should_return_html_page_with_list_of_program() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/dashboard";
        ProgramDto dto = new ProgramDto();
        dto.setId(1L);
        List<ProgramDto> programDtoList = Collections.singletonList(dto);
        String programsJson = mapper.writeValueAsString(programDtoList);
        mockRestServer.expect(requestTo(domainUrl + "/programs"))
                      .andExpect(method(GET))
                      .andRespond(withSuccess(programsJson, APPLICATION_JSON_UTF8));
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("admin/dashboard"))
                     .andExpect(model().attributeExists("newProgram"))
                     .andExpect(model().attribute("programs", hasSize(programDtoList.size())));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void should_return_html_page_after_add_program() throws Exception {
        //GIVEN
        String urlTemplate = "/admin/new_program";
        ProgramDto dto = new ProgramDto();
        dto.setId(1L);
        String programJson = mapper.writeValueAsString(dto);
        mockRestServer.expect(requestTo(domainUrl + "/program"))
                      .andExpect(method(POST))
                      .andRespond(withSuccess(programJson, APPLICATION_JSON_UTF8));
        //WHEN
        ResultActions resultActions = mockMvc
                .perform(post(urlTemplate).contentType(APPLICATION_JSON_UTF8)
                                          .requestAttr("newProgram", new ProgramDtoCreate()));

        //THEN
        resultActions.andExpect(status().is3xxRedirection())
                     .andExpect(redirectedUrl("dashboard"));
    }
}


