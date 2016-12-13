package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;
import com.epam.rd.dto.RoleDto;
import com.epam.rd.dto.UserDto;
import com.epam.rd.dto.UserDtoCreate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter extends AbstractConverter<User, UserDto> {

    public UserDto convert(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't convert user to userDto, user is  null");
        }
        UserDto userDto = new UserDto();
        Objects.requireNonNull(user);
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles().stream()
                .map(s -> RoleDto.valueOf(s.name()))
                .collect(Collectors.toSet()));
        return userDto;
    }


    public User convert(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("Can't convert userDto to user, userDto is  null");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles().stream()
                .map(s -> UserRole.valueOf(s.name()))
                .collect(Collectors.toSet()));
        return user;
    }

    public User convert(UserDtoCreate userDtoCreate) {
        if (userDtoCreate == null) {
            throw new IllegalArgumentException("Can't convert userDtoCreate to user, userDtoCreate is  null");
        }
        User user = new User();
        user.setName(userDtoCreate.getName());
        user.setEmail(userDtoCreate.getEmail());
        user.setPassword(userDtoCreate.getPassword());
        user.setRoles(userDtoCreate.getRoles().stream()
                .map(s -> UserRole.valueOf(s.name()))
                .collect(Collectors.toSet()));
        return user;
    }
}

