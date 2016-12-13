package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.web.config.WebConfig;
import com.epam.rd.dto.RoleDto;
import com.epam.rd.dto.UserDto;
import com.epam.rd.dto.UserDtoCreate;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, SpringCoreConfig.class})
@WebAppConfiguration
@TestPropertySource(value = "classpath:app-config-test.properties")
public class UserControllerIT {

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void set_up() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mapper = new ObjectMapper();
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_all_users() throws Exception {
        //GIVEN
        final String urlTemplate = "/users";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Admin")))
                .andExpect(jsonPath("$[0].password", is(
                        "$2a$10$tHN12DhKhH7zSKrFqrxMReZTOQz1rX8AcpDua4WNgBGMa3Sd8mi2K")))
                .andExpect(jsonPath("$[0].email", is("admin@mail.com")));
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_list_users_by_role() throws Exception {
        //GIVEN
        final String urlTemplate = "/users/" + "MANAGER";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Admin")))
                .andExpect(jsonPath("$[0].password", is(
                        "$2a$10$tHN12DhKhH7zSKrFqrxMReZTOQz1rX8AcpDua4WNgBGMa3Sd8mi2K")))
                .andExpect(jsonPath("$[0].email", is("admin@mail.com")));
    }


    @Test
    @Transactional
    public void add_user() throws Exception {
        //GIVEN
        final String email = "new_user@mail.com";
        final String urlTemplate = "/user/";
        final UserDtoCreate user = new UserDtoCreate();
        user.setName("New User");
        user.setEmail(email);
        user.setPassword("111");
        user.setRoles(Collections.singleton(RoleDto.MANAGER));
        final String userJson = mapper.writeValueAsString(user);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(userJson));
        //THEN
        final String value = "http://localhost/api/user/" + email;
        resultActions.andExpect(status().isCreated())
                .andExpect(header().string("location", value));
    }

    @Test
    @Ignore("Should prepare database before running test")
    public void should_return_user_by_email() throws Exception {
        //GIVEN
        final String urlTemplate = "/user/" + "admin@mail.com";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Admin")))
                .andExpect(jsonPath("$.password", is(
                        "$2a$10$tHN12DhKhH7zSKrFqrxMReZTOQz1rX8AcpDua4WNgBGMa3Sd8mi2K")))
                .andExpect(jsonPath("$.email", is("admin@mail.com")));
    }


    @Test
    @Transactional
    @Ignore("Should prepare database before running test")
    public void update_user_by_email() throws Exception {
        //GIVEN
        final String email = "admin@mail.com";
        final String newEmail = "admin_new@mail.com";
        final String urlTemplate = "/user/" + email;
        UserDto user = new UserDto();
        user.setEmail(newEmail);
        user.setName("TestName");
        user.setPassword("77777777777");
        final String userJson = mapper.writeValueAsString(user);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(userJson));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is("TestName")))
                .andExpect(jsonPath("$.email", is(newEmail)))
                .andExpect(jsonPath("$.password", is("77777777777")));
    }


    @Test
    @Transactional
    @Ignore("Should prepare database before running test")
    public void delete_user_by_email() throws Exception {
        //GIVEN
        final String email = "admin@mail.com";
        final String urlTemplate = "/user/" + email;
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }
}