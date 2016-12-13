package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.UserDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.exception.EntityIsAlreadyExistException;
import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;
import com.epam.rd.backend.core.repository.UserRepository;
import com.epam.rd.backend.core.service.UserService;
import com.epam.rd.dto.UserDto;
import com.epam.rd.dto.UserDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserDtoConverter userDtoConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDtoConverter userDtoConverter) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
    }


    @Override
    public UserDto getUserByEmail(final String userEmail) {
        User userByEmail = userRepository.findUserByEmail(userEmail);
        if (userByEmail == null) {
            throw new EntityDoesNotExistException(String.format("Cannot find user by Email=%s", userEmail));
        }
        return userDtoConverter.convert(userByEmail);
    }

    @Override
    public List<UserDto> getAllUsers() {
        final List<User> users = isUsersExistThenReturnListUsers(userRepository.findAll());
        return userDtoConverter.convertList(users);
    }


    @Override
    public List<UserDto> getUsersByRoles(final Collection<UserRole> roles) {
        final List<User> users = isUsersExistThenReturnListUsers(userRepository.findUsersByRoles(roles));
        return userDtoConverter.convertList(users);
    }

    @Override
    public UserDto addUser(final UserDtoCreate userDto) {
        checkForNotNull(userDto);
        User userByEmail = userRepository.findUserByEmail(userDto.getEmail());
        if (userByEmail != null) {
            throw new EntityIsAlreadyExistException(String.format("User with Email %s is already exists.",
                    userDto.getEmail()));
        }
        return saveUser(userDtoConverter.convert(userDto));
    }

    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        checkForNotNull(userDto);
        checkForNotNull(userDto.getEmail());
        User existedUser = userRepository.findUserByEmail(userDto.getEmail());
        if ((existedUser != null) &&
                (!email.equals(existedUser.getEmail()))) {
            throw new EntityIsAlreadyExistException(String.format("User with new email=%s is already exist",
                    userDto.getEmail()));
        }
        UserDto currentUser = getUserByEmail(email);
        userDto.setEmail(currentUser.getEmail());
        return saveUser(userDtoConverter.convert(userDto));
    }

    private UserDto saveUser(User user) {
        return userDtoConverter.convert(userRepository.save(user));
    }


    private List<User> isUsersExistThenReturnListUsers(final List users) {
        if (users.isEmpty()) {
            throw new EntityDoesNotExistException(String.format("Cannot find users =%s", users.toString()));
        }
        return users;
    }

    private void checkForNotNull(Object dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
    }

    @Override
    public void deleteUserByEmail(final String userEmail) {
        getUserByEmail(userEmail);
        userRepository.deleteUserByEmail(userEmail);
    }

}
