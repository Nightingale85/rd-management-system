package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.exception.EntityIsAlreadyExistException;
import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;
import com.epam.rd.backend.core.repository.UserRepository;
import com.epam.rd.backend.core.service.UserService;
import com.epam.rd.dto.RoleDto;
import com.epam.rd.dto.UserDto;
import com.epam.rd.dto.UserDtoCreate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreConfig.class)
@TestPropertySource(value = "classpath:app-config-test.properties")
public class UserServiceImplIT {

    private final String NAME = "name";
    private final String PASSWORD = "password";
    private final String EMAIL = "test@test.com";
    private final Set<RoleDto> ROLE = Collections.singleton(RoleDto.MANAGER);
    private final Set<UserRole> USER_ROLE = Collections.singleton(UserRole.MANAGER);

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    private UserDto userDto;
    private UserDtoCreate userDtoCreate;

    @Before
    public void init() {
        userDtoCreate = new UserDtoCreate();
        userDtoCreate.setName(NAME);
        userDtoCreate.setPassword(PASSWORD);
        userDtoCreate.setEmail(EMAIL);
        userDtoCreate.setRoles(ROLE);
    }

    @Test
    @Transactional
    public void should_create_user() {
        //WHEN
        userDto = service.addUser(userDtoCreate);
        //THEN
        assertEquals(NAME, userDto.getName());
        assertEquals(PASSWORD, userDto.getPassword());
        assertEquals(EMAIL, userDto.getEmail());
        assertEquals(ROLE, userDto.getRoles());
    }

    @Test(expected = EntityIsAlreadyExistException.class)
    @Transactional
    public void should_throw_exception_when_add_existing_user() {
        //GIVEN
        User user = new User();
        user.setName(NAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setRoles(USER_ROLE);
        repository.save(user);
        //WHEN
        service.addUser(userDtoCreate);
    }

    @Test
    @Transactional
    @Ignore("Should prepare database before running test")
    public void should_get_user_by_email() {
        //GIVEN
        User repoUser = repository.findOne(1L);
        //WHEN
        userDto = service.getUserByEmail(repoUser.getEmail());
        //THEN
        assertEquals(repoUser.getEmail(), userDto.getEmail());
    }

    @Test(expected = EntityDoesNotExistException.class)
    @Transactional
    public void should_throw_exception_during_get_user_by_unknown_email() {
        //GIVEN
        String email = "noEmail@noEmail.com";
        //WHEN
        userDto = service.getUserByEmail(email);
    }

    @Test
    @Transactional
    @Ignore("Should prepare database before running test")
    public void should_return_all_users() {
        //GIVEN
        final List<User> repoUsers = repository.findAll();
        //WHEN
        final List<UserDto> users = service.getAllUsers();
        //THEN
        assertEquals(repoUsers.size(), users.size());
    }

    @Test
    @Transactional
    @Ignore("Should prepare database before running test")
    public void should_return_all_users_by_roles() {
        //GIVEN
        final List<User> repoUsers = repository.findUsersByRoles(USER_ROLE);
        //WHEN
        final List<UserDto> users = service.getUsersByRoles(USER_ROLE);
        //THEN
        assertEquals(repoUsers.size(), users.size());
    }

    @Test
    @Transactional
    public void should_delete_user_by_email() {
        //GIVEN
        User user = new User();
        user.setName(NAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setRoles(USER_ROLE);
        repository.save(user);
        //WHEN
        service.deleteUserByEmail(EMAIL);
        User checkUser = repository.findUserByEmail(EMAIL);
        //THEN
        assertNull(checkUser);
    }

    @Test(expected = EntityDoesNotExistException.class)
    @Transactional
    public void should_throw_exception_during_delete_user_by_unknown_email() {
        //GIVEN
        final String email = "noEmail@noEmail.com";
        //WHEN
        service.deleteUserByEmail(email);
    }

    @Test
    @Transactional
    @Ignore("Should prepare database before running test")
    public void should_update_user_by_email() {
        //GIVEN
        final User repoUser = repository.findOne(1L);
        final String newMail = "newMail@newMail.com";
        String oldMail = repoUser.getEmail();
        UserDto userDto = new UserDto();
        userDto.setName(NAME);
        userDto.setPassword(PASSWORD);
        userDto.setEmail(newMail);
        //WHEN
        service.updateUser(userDto, oldMail);
        //THEN
        assertEquals(newMail, repository.findUserByEmail(newMail).getEmail());
    }

    @Test(expected = EntityDoesNotExistException.class)
    @Transactional
    @Ignore("Should prepare database before running test")
    public void should_throw_exception_during_update_user_by_email() {
        //GIVEN
        final User repoUser = repository.findOne(1L);
        final String newMail = "newMail@newMail.com";
        String oldMail = repoUser.getEmail();
        UserDto userDto = new UserDto();
        userDto.setName(NAME);
        userDto.setPassword(PASSWORD);
        userDto.setEmail(EMAIL);
        //WHEN
        service.updateUser(userDto, newMail);
    }
}