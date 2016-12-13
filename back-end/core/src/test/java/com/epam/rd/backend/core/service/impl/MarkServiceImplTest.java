package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.TempDataFactory;
import com.epam.rd.backend.core.converter.MarkDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.model.Mark;
import com.epam.rd.backend.core.repository.MarkRepository;
import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.MarkDtoCreate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MarkServiceImplTest {

    @Mock
    private MarkRepository markRepository;

    @Mock
    private MarkDtoConverter converter;

    @InjectMocks
    private MarkServiceImpl markService;

    private Mark mark;
    private MarkDto markDto;

    @Before
    public void set_up() throws Exception {
        mark = TempDataFactory.getMark();
        markDto = TempDataFactory.getMarkDto();
    }

    @Test
    public void should_create_mark() {
        //GIVEN
        MarkDtoCreate markDtoCreate = TempDataFactory.getMarkDtoCreate();
        when(converter.convert(markDtoCreate)).thenReturn(mark);
        when(markRepository.save(mark)).thenReturn(mark);
        when(converter.convert(mark)).thenReturn(markDto);

        //WHEN
        MarkDto testMarkDto = markService.createMark(markDtoCreate);

        //THEN
        verify(converter).convert(markDtoCreate);
        verify(markRepository).save(mark);
        verify(converter).convert(mark);
        verifyNoMoreInteractions(converter, markRepository);

        assertEquals(testMarkDto, markDto);
    }

    @Test
    public void should_update_mark() {
        //GIVEN
        when(converter.convert(markDto)).thenReturn(mark);
        when(markRepository.findOne(mark.getId())).thenReturn(mark);
        when(markRepository.save(mark)).thenReturn(mark);
        when(converter.convert(mark)).thenReturn(markDto);

        //WHEN
        MarkDto testMarkDto = markService.updateMark(markDto);

        //THEN
        verify(converter).convert(markDto);
        verify(markRepository).findOne(mark.getId());
        verify(markRepository).save(mark);
        verify(converter).convert(mark);
        verifyNoMoreInteractions(converter, markRepository);

        assertEquals(testMarkDto, markDto);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_throw_exception_in_update_mark() {
        //GIVEN
        when(converter.convert(markDto)).thenReturn(mark);

        //WHEN
        markService.updateMark(markDto);

        //THEN
        verify(converter).convert(markDto);
        verify(markRepository).findOne(mark.getId());
        verifyNoMoreInteractions(converter, markRepository);
    }

    @Test
    public void should_get_mark_by_id() {
        //GIVEN
        when(markRepository.findOne(mark.getId())).thenReturn(mark);
        when(converter.convert(mark)).thenReturn(markDto);

        //WHEN
        MarkDto testMarkDto = markService.getMarkById(mark.getId());

        //THEN
        verify(markRepository).findOne(mark.getId());
        verify(converter).convert(mark);
        verifyNoMoreInteractions(converter, markRepository);

        assertEquals(testMarkDto, markDto);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_throw_exception_in_get_mark_by_id() {
        //WHEN
        markService.getMarkById(mark.getId());

        //THEN
        verify(markRepository).findOne(mark.getId());
        verifyNoMoreInteractions(markRepository);
    }

    @Test
    public void should_delete_mark_by_id() {
        //GIVEN
        when(markRepository.findOne(mark.getId())).thenReturn(mark);

        //WHEN
        markService.deleteMarkById(mark.getId());

        //THEN
        verify(markRepository).findOne(mark.getId());
        verify(markRepository).delete(mark.getId());
        verifyNoMoreInteractions(converter, markRepository);
    }

    @Test
    public void should_return_list_mark_dto_by_mentee_email() {
        //GIVEN
        List<Mark> markList = new ArrayList<>();
        markList.add(mark);
        List<MarkDto> markDtoList = new ArrayList<>();
        markDtoList.add(markDto);
        when(converter.convertList(markList)).thenReturn(markDtoList);
        when(markRepository.findMarksByMenteeEmail(mark.getMenteeEmail())).thenReturn(markList);

        //WHEN
        markService.getMarksByMenteeEmail(mark.getMenteeEmail());

        //THEN
        verify(markRepository).findMarksByMenteeEmail(mark.getMenteeEmail());
        verify(converter).convertList(markList);
        verifyNoMoreInteractions(converter, markRepository);
    }

    @Test
    public void should_return_list_mark_dto_by_estimator_email_and_task_id() {
        //GIVEN
        List<Mark> markList = new ArrayList<>();
        markList.add(mark);
        List<MarkDto> markDtoList = new ArrayList<>();
        markDtoList.add(markDto);
        when(converter.convertList(markList)).thenReturn(markDtoList);
        when(markRepository.findMarksByPracticalTaskIdAndEstimatorEmail(mark.getPracticalTaskId(),
                mark.getEstimatorEmail())).thenReturn(markList);

        //WHEN
        List<MarkDto> marks = markService.getMarksByPracticalTaskIdAndEstimatorEmail(mark.getPracticalTaskId(),
                mark.getEstimatorEmail());

        //THEN
        assertNotNull(marks);
        verify(markRepository).findMarksByPracticalTaskIdAndEstimatorEmail(mark.getPracticalTaskId(),
                mark.getEstimatorEmail());
        verify(converter).convertList(markList);
        verifyNoMoreInteractions(converter, markRepository);
    }
}
