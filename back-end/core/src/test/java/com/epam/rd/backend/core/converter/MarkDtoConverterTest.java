package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.TempDataFactory;
import com.epam.rd.backend.core.model.Mark;
import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.MarkDtoCreate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MarkDtoConverterTest {
    private MarkDtoConverter converter = new MarkDtoConverter();

    @Test
    public void should_convert_mark_to_mark_dto() {
        //GIVEN
        Mark mark = TempDataFactory.getMark();
        //WHEN
        MarkDto markDto = converter.convert(mark);
        //THEN
        assertMarkEquals(mark, markDto);
    }

    @Test
    public void should_convert_mark_dto_to_mark() {
        //GIVEN
        MarkDto markDto = TempDataFactory.getMarkDto();
        //WHEN
        Mark mark = converter.convert(markDto);
        //THEN
        assertMarkEquals(mark, markDto);
    }

    @Test
    public void should_convert_mark_dto_create_to_mark() {
        //GIVEN
        MarkDtoCreate markDtoCreate = TempDataFactory.getMarkDtoCreate();
        //WHEN
        Mark mark = converter.convert(markDtoCreate);
        //THEN
        assertMarkEquals(mark, markDtoCreate);
    }

    @SuppressWarnings("checkstyle:methodname")
    private void assertMarkEquals(Mark mark, MarkDto markDto) {
        assertEquals(mark.getId(), markDto.getId());
        assertEquals(mark.getPracticalTaskId(), markDto.getPracticalTaskId());
        assertEquals(mark.getMenteeEmail(), markDto.getMenteeEmail());
        assertEquals(mark.getEstimatorEmail(), markDto.getEstimatorEmail());
        assertEquals(mark.getEstimatorRole().name(), markDto.getEstimatorRole().name());
        assertEquals(mark.getMark(), markDto.getMark());
        assertEquals(mark.getFeedback(), markDto.getFeedback());
    }

    @SuppressWarnings("checkstyle:methodname")
    private void assertMarkEquals(Mark mark, MarkDtoCreate markDtoCreate) {
        assertEquals(mark.getPracticalTaskId(), markDtoCreate.getPracticalTaskId());
        assertEquals(mark.getMenteeEmail(), markDtoCreate.getMenteeEmail());
        assertEquals(mark.getEstimatorEmail(), markDtoCreate.getEstimatorEmail());
        assertEquals(mark.getEstimatorRole().name(), markDtoCreate.getEstimatorRole().name());
        assertEquals(mark.getMark(), markDtoCreate.getMark());
        assertEquals(mark.getFeedback(), markDtoCreate.getFeedback());
    }

}