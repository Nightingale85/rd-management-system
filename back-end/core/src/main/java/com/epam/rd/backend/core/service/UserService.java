package com.epam.rd.backend.core.service;

import com.epam.rd.backend.core.model.UserRole;
import com.epam.rd.dto.UserDto;
import com.epam.rd.dto.UserDtoCreate;

import java.util.Collection;
import java.util.List;

public interface UserService {

    UserDto getUserByEmail(String userEmail);

    List<UserDto> getAllUsers();

    List<UserDto> getUsersByRoles(Collection<UserRole> roles);

    UserDto addUser(UserDtoCreate user);

    UserDto updateUser(UserDto user, String email);

    void deleteUserByEmail(String userEmail);


}
