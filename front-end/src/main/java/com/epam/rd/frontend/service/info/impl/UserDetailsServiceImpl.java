package com.epam.rd.frontend.service.info.impl;

import com.epam.rd.dto.UserDto;
import com.epam.rd.frontend.converter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserDtoConverter converter;
    @Override
    public UserDetails loadUserByUsername(final String email) {
        final UserDto userDto = restTemplate.getForObject("/user/" + email, UserDto.class);
        return converter.convert(userDto);
    }
}
