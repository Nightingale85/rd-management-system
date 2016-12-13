package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.UserDtoConverter;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String NAME = "user";
    private static final String EMAIL = "user@test.com";
    private static final String PASSWORD = "password";
    private static final Set<RoleDto> ROLES_DTO = Collections.singleton(RoleDto.MANAGER);
    private static final Set<UserRole> USER_ROLES = Collections.singleton(UserRole.MANAGER);

    @Mock
    private UserRepository repositoryMock;
    @Mock
    private UserDtoConverter converterMock;

    private UserService service;

    @Before
    public void set_up() throws Exception {
        service = new UserServiceImpl(repositoryMock, converterMock);
    }

    @Test
    public void should_return_user_by_email() {
        //GIVEN
        User user = new User();
        UserDto userDto = new UserDto();
        when(repositoryMock.findUserByEmail(EMAIL)).thenReturn(user);

        //WHEN
        userDto = service.getUserByEmail(EMAIL);

        //THEN
        verify(repositoryMock, times(1)).findUserByEmail(EMAIL);
        verify(converterMock, times(1)).convert(user);
    }

    @Test
    public void should_create_user() {
        //GIVEN
        User user = new User();

        UserDto userDto = new UserDto();
        userDto.setName(NAME);
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);

        UserDtoCreate userDtoCreate = new UserDtoCreate();
        userDtoCreate.setName(NAME);
        userDtoCreate.setEmail(EMAIL);
        userDtoCreate.setPassword(PASSWORD);
        userDtoCreate.setRoles(ROLES_DTO);

        when(converterMock.convert(userDtoCreate)).thenReturn(user);
        when(repositoryMock.save(user)).thenReturn(user);
        when(converterMock.convert(user)).thenReturn(userDto);

        //WHEN
        UserDto newUserDto = service.addUser(userDtoCreate);

        //THEN
        verify(converterMock, times(1)).convert(userDtoCreate);
        verify(converterMock, times(1)).convert(user);
        verify(repositoryMock, times(1)).save(user);
    }

    @Test(expected = EntityIsAlreadyExistException.class)
    public void should_throw_exception_when_add_user_with_existing_email() {
        //GIVEN
        User user = new User();

        UserDtoCreate userDtoCreate = new UserDtoCreate();
        userDtoCreate.setName(NAME);
        userDtoCreate.setEmail(EMAIL);
        userDtoCreate.setPassword(PASSWORD);
        userDtoCreate.setRoles(ROLES_DTO);

        when(repositoryMock.findUserByEmail(EMAIL)).thenReturn(user);

        //WHEN
        service.addUser(userDtoCreate);
    }

    @Test
    public void should_return_all_users() {
        //GIVEN
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(repositoryMock.findAll()).thenReturn(users);

        //WHEN
        List<UserDto> userDtoList = service.getAllUsers();

        //THEN
        verify(repositoryMock, times(1)).findAll();
        verify(converterMock, times(1)).convertList(users);
    }

    @Test
    public void should_delete_user_by_email() {
        //GIVEN
        User user = new User();
        UserDto userDto = new UserDto();
        when(repositoryMock.findUserByEmail(EMAIL)).thenReturn(user);
        when(service.getUserByEmail(EMAIL)).thenReturn(userDto);

        //WHEN
        service.deleteUserByEmail(EMAIL);

        //THEN
        verify(repositoryMock, times(1)).deleteUserByEmail(EMAIL);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_throw_exception_when_user_must_be_deleted_but_not_found() {
        //GIVEN
        when(repositoryMock.findUserByEmail(EMAIL)).thenReturn(null);

        //WHEN
        service.deleteUserByEmail(EMAIL);
    }

    @Test
    public void should_return_list_of_users_by_role() {
        //GIVEN
        List<User> users = new ArrayList<>();
        users.add(new User());
        List<UserDto> userDtoList = new ArrayList<>();
        when(repositoryMock.findUsersByRoles(USER_ROLES)).thenReturn(users);

        //WHEN
        userDtoList = service.getUsersByRoles(USER_ROLES);

        //THEN
        verify(repositoryMock, times(1)).findUsersByRoles(USER_ROLES);
        verify(converterMock, times(1)).convertList(users);
    }

    @Test
    public void should_update_user_by_email() {
        //GIVEN
        final String newName = "newName";
        User user = new User();
        user.setEmail(EMAIL);

        UserDto userDto = new UserDto();
        userDto.setName(newName);
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);

        when(converterMock.convert(userDto)).thenReturn(user);
        when(repositoryMock.save(user)).thenReturn(user);
        when(converterMock.convert(user)).thenReturn(userDto);
        when(repositoryMock.findUserByEmail(anyString())).thenReturn(user);
        when(converterMock.convert(user)).thenReturn(userDto);

        //WHEN
        UserDto newUserDto = service.updateUser(userDto, EMAIL);

        //THEN
        verify(repositoryMock, times(2)).findUserByEmail(EMAIL);
        verify(converterMock, times(2)).convert(user);
        verify(converterMock, times(1)).convert(userDto);
        verify(repositoryMock, times(1)).save(user);
    }
}