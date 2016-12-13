package com.epam.rd.frontend.controller.error;

import com.epam.rd.dto.ExceptionDto;
import com.epam.rd.frontend.config.SpringConfig;
import com.epam.rd.frontend.config.WebConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, WebConfig.class})
@WebAppConfiguration
@TestPropertySource("classpath:config.properties")
public class ExceptionControllerAdviceIT {

    @Value("${url.domain}")
    private String domainUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockRestServiceServer server;
    private MockMvc mockMvc;

    @Before
    public void set_up() {
        server = MockRestServiceServer.createServer(restTemplate);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_return_error_page_by_wrong_lecture_id() throws Exception {
        //GIVEN
        ExceptionDto exceptionDto = new ExceptionDto("Lecture with such ID does not exist", RuntimeException.class);
        String exceptionJson = mapper.writeValueAsString(exceptionDto);
        Long id = Long.MAX_VALUE;
        server.expect(requestTo(domainUrl + "/lecture/" + id))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(exceptionJson)
                        .contentType(APPLICATION_JSON_UTF8));
        //WHEN
        ResultActions resultActions = mockMvc.perform(get("/info/lecture/" + id));
        //THEN
        server.verify();
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("error/error"))
                .andExpect(model().attributeExists("exception"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    public void should_return_error_page_when_exception_thrown() throws Exception {
        //GIVEN
        final String url = "/admin/task/edit";
        //WHEN
        ResultActions resultAction = mockMvc.perform(put(url)
                .sessionAttr("task", ""));
        //THEN
        resultAction.andExpect(status().isOk())
                .andExpect(view().name("error/error"))
                .andExpect(model().attributeExists("exception"))
                .andExpect(model().attributeExists("message"));
    }
}
