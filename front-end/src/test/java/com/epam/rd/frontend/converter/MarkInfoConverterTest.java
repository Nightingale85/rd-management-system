package com.epam.rd.frontend.converter;

import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.RoleDto;
import com.epam.rd.frontend.model.MarkInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.epam.rd.frontend.TempDataFactory.markDto;
import static org.junit.Assert.assertEquals;

public class MarkInfoConverterTest {

    @Test
    public void should_convert_user_to_list_mark_info() {
        //GIVEN
        List<MarkDto> markDtoList = new ArrayList<>();
        MarkDto markDto1 = markDto(RoleDto.MENTOR, "mentor@email.com");
        MarkDto markDto2 = markDto(RoleDto.LECTURER, "lecturer@email.com");
        markDtoList.add(markDto1);
        markDtoList.add(markDto2);
        MarkInfoConverter converter = new MarkInfoConverter();

        //WHEN
        MarkInfo markInfo = converter.convert(markDtoList);

        //THEN
        assertEquals(markDto1.getFeedback(), markInfo.getMentorFeedback());
        assertEquals(markDto1.getMark(), markInfo.getMentorMark());
        assertEquals(markDto2.getFeedback(), markInfo.getLecturerFeedback());
        assertEquals(markDto2.getMark(), markInfo.getLecturerMark());
    }
}
