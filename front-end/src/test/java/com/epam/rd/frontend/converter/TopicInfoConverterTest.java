package com.epam.rd.frontend.converter;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.frontend.model.TopicInfo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TopicInfoConverterTest {
    private static final Long TOPIC_ID = 1L;
    private static final String TITLE = "Title";

    private TopicInfoConverter converter = new TopicInfoConverter();

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_exception_when_try_convert_topic_dto_as_null() {
        //WHEN
        converter.convert(null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_exception_when_try_convert_topic_dto_with_id_null() {
        //WHEN
        converter.convert(new TopicDto(), null, null);
    }

    @Test
    public void should_return_topic_info_with_topic_id() {
        //GIVEN
        TopicDto topicDto = new TopicDto();
        topicDto.setId(TOPIC_ID);
        //WHEN
        TopicInfo topicInfo = converter.convert(topicDto, null, null);
        //THEN
        assertEquals(TOPIC_ID, topicInfo.getTopicId());
    }

    @Test
    public void should_return_topic_info_with_topic_title() {
        //GIVEN
        TopicDto topicDto = new TopicDto();
        topicDto.setId(TOPIC_ID);
        topicDto.setTitle(TITLE);
        //WHEN
        TopicInfo topicInfo = converter.convert(topicDto, null, null);
        //THEN
        assertEquals(TITLE, topicInfo.getTopicTitle());
    }

    @Test
    public void should_return_topic_info_with_lecture_title() {
        //GIVEN
        TopicDto topicDto = new TopicDto();
        topicDto.setId(TOPIC_ID);
        LectureDto lectureDto = new LectureDto();
        lectureDto.setTitle(TITLE);
        //WHEN
        TopicInfo topicInfo = converter.convert(topicDto, lectureDto, null);
        //THEN
        assertEquals(TITLE, topicInfo.getLectureTitle());
    }

    @Test
    public void should_return_topic_info_with_practical_task_title() {
        //GIVEN
        TopicDto topicDto = new TopicDto();
        topicDto.setId(TOPIC_ID);
        PracticalTaskDto taskDto = new PracticalTaskDto();
        taskDto.setTitle(TITLE);
        //WHEN
        TopicInfo topicInfo = converter.convert(topicDto, null, taskDto);
        //THEN
        assertEquals(TITLE, topicInfo.getTaskTitle());
    }
}