package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.model.UserRole;
import com.epam.rd.backend.core.service.UserService;
import com.epam.rd.backend.web.utill.CaseInsensitiveUserRoleEditor;
import com.epam.rd.dto.UserDto;
import com.epam.rd.dto.UserDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static com.epam.rd.HeaderConstant.HEADER_USER_BK;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(UserRole.class, new CaseInsensitiveUserRoleEditor());
    }

    @RequestMapping(value = "/users", method = GET)
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<UserDto> userDtoList = service.getAllUsers();
        HttpStatus status = OK;
        if (userDtoList.isEmpty()) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(userDtoList);
    }

    @RequestMapping(value = "/users/{role}", method = GET)
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable("role") String role) {
        List<UserDto> userDtoList = service.getUsersByRoles(Collections.singleton(UserRole.of(role)));
        HttpStatus status = OK;
        if (userDtoList.isEmpty()) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(userDtoList);
    }

    @RequestMapping(value = "/user/{email:.+}", method = GET)
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        UserDto dto = service.getUserByEmail(email);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/user/", method = POST)
    public ResponseEntity<UserDto> addUser(@RequestBody UserDtoCreate dtoCreate,
                                           UriComponentsBuilder builder) {
        UserDto dto = service.addUser(dtoCreate);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/api/user/{email:.+}").buildAndExpand(dto.getEmail()).toUri());
        headers.add(HEADER_USER_BK, dto.getEmail());
        return ResponseEntity.status(CREATED).headers(headers).body(dto);
    }


    @RequestMapping(value = "/user/{email:.+}", method = PUT)
    public ResponseEntity<UserDto> updateUserByEmail(@PathVariable("email") String email,
                                                     @RequestBody UserDto userDto) {
        UserDto updateUser = service.updateUser(userDto, email);
        return ResponseEntity.ok(updateUser);
    }

    @RequestMapping(value = "/user/{email:.+}", method = DELETE)
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable("email") String email) {
        service.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }

}