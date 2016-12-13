package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.PracticalTask;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;
import org.junit.Test;

import static org.junit.Assert.*;

public class PracticalTaskDtoConverterTest {
    private static final long ID = 1L;
    private static final String DESCRIPTION = "Description";
    private static final String TITLE = "Practical Task";
    private PracticalTaskDtoConverter converter = new PracticalTaskDtoConverter();

    @Test
    public void should_convert_task_to_dto() {
        //GIVEN
        PracticalTask task = new PracticalTask();
        task.setId(ID);
        task.setName(TITLE);
        task.setLinkToDescription(DESCRIPTION);
        Topic topic = new Topic();
        topic.setId(ID);
        topic.setPracticalTask(task);
        //WHEN
        PracticalTaskDto dto = converter.convert(task);
        //THEN
        assertEquals(task.getId(), dto.getId());
        assertEquals(task.getName(), dto.getTitle());
        assertEquals(task.getLinkToDescription(), dto.getDescription());
        assertEquals(task.getTopic().getId(), dto.getTopicId());
    }

    @Test
    public void should_convert_dto_to_task() {
        //GIVEN
        PracticalTaskDto dto = new PracticalTaskDto();
        dto.setId(ID);
        //WHEN
        PracticalTask task = converter.convert(dto);
        //THEN
        assertEquals(dto.getId(), task.getId());
    }

    @Test(expected = NullPointerException.class)
    public void should_trow_npe_if_dto_as_null_converting_to_task() {
        //GIVEN
        PracticalTaskDto dto = null;
        //WHEN
        converter.convert(dto);
        //THEN
        fail();
    }

    @Test
    public void should_convert_dto_for_create_to_task() {
        //GIVEN
        PracticalTaskDtoCreate dto = new PracticalTaskDtoCreate();
        dto.setTitle(TITLE);
        //WHEN
        PracticalTask task = converter.convert(dto);
        //THEN
        assertEquals(dto.getTitle(), task.getName());
    }

    @Test(expected = NullPointerException.class)
    public void should_trow_npe_if_dto_for_create_as_null_converting_to_task() {
        //GIVEN
        PracticalTaskDtoCreate dto = null;
        //WHEN
        converter.convert(dto);
        //THEN
        fail();
    }

    @Test
    public void should_return_task_with_topic_as_null_from_dto() {
        //GIVEN
        PracticalTaskDto dto = new PracticalTaskDto();
        dto.setTopicId(ID);
        //WHEN
        PracticalTask task = converter.convert(dto);
        //THEN
        assertNull(task.getTopic());
    }

    @Test
    public void should_return_task_with_topic_as_null_from_dto_for_create() {
        //GIVEN
        PracticalTaskDtoCreate dto = new PracticalTaskDtoCreate();
        dto.setTopicId(ID);
        //WHEN
        PracticalTask task = converter.convert(dto);
        //THEN
        assertNull(task.getTopic());
    }
}