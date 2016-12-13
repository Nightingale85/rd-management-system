package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;
import com.epam.rd.dto.RoleDto;
import com.epam.rd.dto.UserDto;
import com.epam.rd.dto.UserDtoCreate;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UserDtoConverterTest {

    private User user;
    private Set<UserRole> roles;
    private UserDto userDto;
    private Set<RoleDto> rolesDtoSet;
    private final String NAME = "Name";
    private final String EMAIL = "email@test.com";
    private final String PASSWORD = "Password";
    private final Long ID = 1L;
    private final RoleDto ROLE_MENTEE = RoleDto.MENTEE;
    private final RoleDto ROLE_LECTURER = RoleDto.LECTURER;


    @Before
    public void set_up() {
        roles = new HashSet<>();
        roles.add(UserRole.LECTURER);
        roles.add(UserRole.MENTEE);

        rolesDtoSet = new HashSet<>();
        rolesDtoSet.add(ROLE_MENTEE);
        rolesDtoSet.add(ROLE_LECTURER);

        user = new User();
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setRoles(roles);

        userDto = new UserDto();
        userDto.setName(NAME);
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);
        userDto.setRoles(rolesDtoSet);
    }

    private void assert_dto_converted_to_model(UserDto userDto, User user) {
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.getRoles().size(), user.getRoles().size());
    }

    @Test
    public void should_convert_user_dto_to_user() throws Exception {
        //WHEN
        UserDtoConverter userDtoConverter = new UserDtoConverter();
        //GIVEN
        User user = userDtoConverter.convert(userDto);
        //THEN
        assert_dto_converted_to_model(userDto, user);
    }

    @Test
    public void should_convert_user_to_user_dto() throws Exception {
        //WHEN
        UserDtoConverter userDtoConverter = new UserDtoConverter();
        //GIVEN
        UserDto userDto = userDtoConverter.convert(user);
        //THEN
        assert_dto_converted_to_model(userDto, user);
    }

    @Test
    public void should_convert_list_user_to_list_user_dto() {
        //WHEN
        Set<UserRole> roles1 = new HashSet<>();
        roles1.add(UserRole.LECTURER);
        roles1.add(UserRole.MENTEE);

        User user1 = new User();
        user1.setName("NewName");
        user1.setEmail("newEmail@test.com");
        user1.setPassword("NewPassword");
        user1.setRoles(roles1);

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        //GIVEN
        UserDtoConverter userDtoConverter = new UserDtoConverter();
        List<UserDto> usersDto = userDtoConverter.convertList(users);
        //THEN
        assert_dto_converted_to_model(usersDto.get(0), users.get(0));
        assert_dto_converted_to_model(usersDto.get(1), users.get(1));
    }

    @Test
    public void should_convert_user_dto_create_to_user() throws Exception {
        //WHEN
        UserDtoCreate userDtoCreate = new UserDtoCreate();
        userDtoCreate.setName(NAME);
        userDtoCreate.setEmail(EMAIL);
        userDtoCreate.setPassword(PASSWORD);
        userDtoCreate.setRoles(rolesDtoSet);
        UserDtoConverter userDtoConverter = new UserDtoConverter();
        //GIVEN
        User user = userDtoConverter.convert(userDtoCreate);
        //THEN
        assertEquals(userDtoCreate.getName(), user.getName());
        assertEquals(userDtoCreate.getEmail(), user.getEmail());
        assertEquals(userDtoCreate.getPassword(), user.getPassword());
        assertEquals(userDtoCreate.getRoles().size(), user.getRoles().size());
    }
}