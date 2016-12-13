package com.epam.rd.frontend.service.info;

import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.RoleDto;
import com.epam.rd.frontend.converter.MarkInfoConverter;
import com.epam.rd.frontend.model.MarkInfo;
import com.epam.rd.frontend.service.MarkService;
import com.epam.rd.frontend.service.PracticalTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.epam.rd.frontend.TempDataFactory.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MarkInfoFacadeTest {
    @Mock
    private MarkService markService;

    @Mock
    private PracticalTaskService practicalTaskService;

    @Mock
    private MarkInfoConverter markInfoConverter;

    @InjectMocks
    private MarkInfoFacade markInfoFacade;

    @Test
    public void should_return_list_mark_info_by_mentee_email() {
        //GIVEN
        String email = "mentee@email.com";
        Long id = 1L;
        List<MarkDto> markDtoList = new ArrayList<>();
        markDtoList.add(markDto(RoleDto.MENTOR, "mentor@email.com"));
        markDtoList.add(markDto(RoleDto.LECTURER, "lecturer@email.com"));
        PracticalTaskDto task = getPracticalTaskDto(id, id);
        MarkInfo markInfo = markInfo();
        List<MarkInfo> expectedMarkInfoList = new ArrayList<>();
        expectedMarkInfoList.add(markInfo);

        when(markService.getMarksByMenteeEmail(email)).thenReturn(markDtoList);
        when(practicalTaskService.getPracticalTaskDtoById(id)).thenReturn(task);
        when(markInfoConverter.convert(markDtoList)).thenReturn(markInfo);

        //WHEN
        List<MarkInfo> actualMarkInfoList = markInfoFacade.getMarkInfoListByMentee(email);

        //THEN
        assertEquals(expectedMarkInfoList, actualMarkInfoList);

        verify(markService).getMarksByMenteeEmail(email);
        verify(practicalTaskService).getPracticalTaskDtoById(id);
        verify(markInfoConverter).convert(markDtoList);
    }
}
