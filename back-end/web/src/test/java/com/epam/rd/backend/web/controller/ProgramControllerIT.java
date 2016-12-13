package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.model.Program;
import com.epam.rd.backend.core.repository.ProgramRepository;
import com.epam.rd.backend.web.config.WebConfig;
import com.epam.rd.backend.web.controller.populator.TempDataFactory;
import com.epam.rd.dto.ProgramDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, SpringCoreConfig.class})
@WebAppConfiguration
@TestPropertySource(value = "classpath:app-config-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProgramControllerIT {
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProgramRepository repository;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    @Transactional
    public void should_return_all_program() throws Exception {
        //GIVEN
        final String urlTemplate = "/programs";
        repository.save(TempDataFactory.createProgram());
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Name 1")))
                .andExpect(jsonPath("$[0].description", is("Description 1")))
                .andExpect(jsonPath("$[0].startDate", is("2016-01-01")))
                .andExpect(jsonPath("$[0].endDate", is("2016-06-01")))
                .andExpect(jsonPath("$[0].modulesId", empty()));
    }

    @Test
    @Transactional
    public void should_return_program_with_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/program/1";
        repository.save(TempDataFactory.createProgram());
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Name 1")))
                .andExpect(jsonPath("$.description", is("Description 1")))
                .andExpect(jsonPath("$.startDate", is("2016-01-01")))
                .andExpect(jsonPath("$.endDate", is("2016-06-01")));
    }

    @Test
    @Transactional
    public void should_throw_exception_for_unknown_program_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/program/123";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    public void add_program_to_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/program/";
        repository.save(TempDataFactory.createProgram());
        final String programJson = mapper.writeValueAsString(TempDataFactory.createProgramDtoCreate());
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(programJson));
        //THEN
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("Name 2")))
                .andExpect(jsonPath("$.description", is("Description 2")))
                .andExpect(jsonPath("$.startDate", is("2016-01-01")))
                .andExpect(jsonPath("$.endDate", is("2016-12-01")));
    }

    @Test
    @Transactional
    public void update_program_in_database_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/program/1";
        repository.save(TempDataFactory.createProgram());

        final ProgramDto programDto = new ProgramDto();
        Program program = repository.findOne(1L);
        programDto.setId(program.getId());
        programDto.setTitle("Name 1 New");
        programDto.setDescription(program.getLinkToDescription());
        programDto.setStartDate(program.getDateStart());
        programDto.setEndDate(program.getDateEnd());

        final String programJson = mapper.writeValueAsString(programDto);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(programJson));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Name 1 New")))
                .andExpect(jsonPath("$.description", is("Description 1")))
                .andExpect(jsonPath("$.startDate", is("2016-01-01")))
                .andExpect(jsonPath("$.endDate", is("2016-06-01")))
                .andExpect(jsonPath("$.modulesId", empty()));
    }

    @Test
    @Transactional
    public void should_delete_program_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/program/1";
        repository.save(TempDataFactory.createProgram());
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }
}
