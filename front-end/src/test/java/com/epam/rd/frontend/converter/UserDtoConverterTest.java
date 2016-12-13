package com.epam.rd.frontend.converter;

import com.epam.rd.dto.UserDto;
import com.epam.rd.frontend.model.CurrentUser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserDtoConverterTest {

    private UserDtoConverter converter = new UserDtoConverter();

    @Test
    public void should_convert_user_dto_to_current_user() {

        //GIVEN
        UserDto userDto = new UserDto();
        userDto.setName("mentee");
        userDto.setEmail("mentee@mentee");
        userDto.setPassword("mentee");
        //WHEN
        CurrentUser currentUser = converter.convert(userDto);
        //THEN
        assertEquals(currentUser.getUsername(), userDto.getEmail());
        assertEquals(currentUser.getPassword(), userDto.getPassword());
    }
}
