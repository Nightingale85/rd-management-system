package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.service.ModuleService;
import com.epam.rd.backend.web.config.WebConfig;
import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ModuleDtoCreate;
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
public class ModuleControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModuleController moduleController;

    @Before
    public void set_up() {
        mockMvc = MockMvcBuilders.standaloneSetup(moduleController).build();
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_all_module() throws Exception {
        //GIVEN
        final String urlTemplate = "/modules";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("ModuleName1")))
                .andExpect(jsonPath("$[0].description", is("LinkToDescription1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("ModuleName2")))
                .andExpect(jsonPath("$[1].description", is("LinkToDescription2")));
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_module_with_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/1";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                     .andExpect(jsonPath("$.id", is(1)))
                     .andExpect(jsonPath("$.title", is("ModuleName1")))
                     .andExpect(jsonPath("$.description", is("LinkToDescription1")));
    }

    @Test(expected = Exception.class)
    public void should_return_status_not_found_if_module_id_absent() throws Exception {
        //GIVEN
        String urlTemplate = "/module/" + Integer.MAX_VALUE;
        //WHEN
        mockMvc.perform(get(urlTemplate));
        //THEN
        fail();
    }

    @Test
    public void add_module_to_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/module";
        final ModuleDtoCreate module = new ModuleDtoCreate();
        module.setTitle("New Module");
        module.setDescription("new Link to Description");
        module.setProgramId(1L);
        final ObjectMapper mapper = new ObjectMapper();
        final String moduleJson = mapper.writeValueAsString(module);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                                                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                                    .content(moduleJson));
        //THEN
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void update_module_in_database_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/2";
        ModuleDto dto = moduleService.getModuleById(2L);
        dto.setTitle("new Module Name");
        final ObjectMapper mapper = new ObjectMapper();
        final String moduleJson = mapper.writeValueAsString(dto);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                                                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                                    .content(moduleJson));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                     .andExpect(jsonPath("$.id", is(2)))
                     .andExpect(jsonPath("$.title", is("new Module Name")))
                     .andExpect(jsonPath("$.description", is("LinkToDescription2")));
    }

    @Test(expected = Exception.class)
    public void try_update_not_existed_module_by_id() throws Exception {
        //GIVEN
        String urlTemplate = "/module/" + Integer.MAX_VALUE;
        ModuleDto dto = new ModuleDto();
        ObjectMapper mapper = new ObjectMapper();
        String moduleJson = mapper.writeValueAsString(dto);
        //WHEN
        ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                                                              .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                              .content(moduleJson));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void delete_module_in_database_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/2";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }

    @Test(expected = Exception.class)
    public void try_delete_module_not_existed_module_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/" + Integer.MAX_VALUE;
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_list_of_modules_by_program_id() throws Exception {
        //GIVEN
        String url = "/program/1/modules";
        //WHEN
        ResultActions resultActions = mockMvc.perform(get(url));
        //THEN
        resultActions.andExpect(status().isOk());
    }
}