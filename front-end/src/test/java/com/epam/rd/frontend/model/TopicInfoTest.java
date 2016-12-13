package com.epam.rd.frontend.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TopicInfoTest {
    private static final long TOPIC_ID = 1L;
    private static final String TITLE = "Title";

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_exception_if_create_new_topic_info_with_null_in_topic_id() {
        //WHEN
        new TopicInfo(null, null, null, null);
    }

    @Test
    public void should_return_topic_id() {
        //GIVEN
        Long expectedId = TOPIC_ID;
        TopicInfo topicInfo = new TopicInfo(expectedId, null, null, null);
        //WHEN
        Long actualId = topicInfo.getTopicId();
        //THEN
        assertEquals(expectedId, actualId);
    }

    @Test
    public void should_return_topic_title() {
        //GIVEN
        String expectedTitle = TITLE;
        TopicInfo topicInfo = new TopicInfo(TOPIC_ID, expectedTitle, null, null);
        //WHEN
        String actualTitle = topicInfo.getTopicTitle();
        //THEN
        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void should_return_lecture_title() {
        //GIVEN
        String expectedTitle = TITLE;
        TopicInfo topicInfo = new TopicInfo(TOPIC_ID, null, expectedTitle, null);
        //WHEN
        String actualTitle = topicInfo.getLectureTitle();
        //THEN
        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void should_return_task_title() {
        //GIVEN
        String expectedTitle = TITLE;
        TopicInfo topicInfo = new TopicInfo(TOPIC_ID, null, null, expectedTitle);
        //WHEN
        String actualTitle = topicInfo.getTaskTitle();
        //THEN
        assertEquals(expectedTitle, actualTitle);
    }

}