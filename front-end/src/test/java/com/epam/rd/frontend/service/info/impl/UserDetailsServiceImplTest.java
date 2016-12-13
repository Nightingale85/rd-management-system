package com.epam.rd.frontend.service.info.impl;

import com.epam.rd.dto.UserDto;
import com.epam.rd.frontend.converter.UserDtoConverter;
import com.epam.rd.frontend.model.CurrentUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {
    @Mock
    private RestTemplate restTemplateMock;
    @Mock
    private UserDtoConverter converterMock;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    private String email;
    private UserDto userDto;
    private CurrentUser user;
    @Before
    public void set_up() {
        email = "admin@mail.com";
        UserDto userDto = new UserDto();
        userDto.setName("mentee");
        userDto.setEmail("mentee@mentee");
        userDto.setPassword("mentee");
        CurrentUser user = new CurrentUser("mentee", "mentee@mentee", new ArrayList<GrantedAuthority>());
    }

    @Test
    public void should_load_user_by_username() {
        //GIVEN
        when(restTemplateMock.getForObject(anyString(), eq(UserDto.class))).thenReturn(userDto);
        when(converterMock.convert(userDto)).thenReturn(user);
        //WHEN
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        //THEN
        verify(restTemplateMock, times(1)).getForObject(anyString(), eq(UserDto.class));
        verify(converterMock, times(1)).convert(userDto);
    }
}
