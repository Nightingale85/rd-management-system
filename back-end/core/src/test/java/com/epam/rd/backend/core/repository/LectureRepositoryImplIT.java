package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.TempDataFactory;
import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.model.Lecture;
import com.epam.rd.backend.core.model.Topic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreConfig.class)
@TestPropertySource(value = "classpath:app-config-test.properties")
public class LectureRepositoryImplIT {
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Test
    @Transactional
    public void should_return_lecture_by_topic_id() {
        //GIVEN
        Lecture givenLecture = TempDataFactory.getLecture();
        Topic givenTopic = TempDataFactory.getTopic();
        givenTopic.setLecture(givenLecture);
        topicRepository.save(givenTopic);
        //WHEN
        Lecture currentLecture = lectureRepository.findLectureByTopicId(givenTopic.getId());
        Topic currentTopic = topicRepository.findOne(givenTopic.getId());
        //THEN
        assertNotNull("Lecture should not be NULL", currentLecture);
        assertEquals(givenLecture.getId(), currentLecture.getId());
        assertEquals(givenLecture.getDateStart(), currentLecture.getDateStart());
        assertEquals(givenLecture.getDescription(), currentLecture.getDescription());
        assertEquals(givenLecture.getTypeOfPlace(), currentLecture.getTypeOfPlace());
        assertEquals(givenLecture.getLinkTopicEpam(), currentLecture.getLinkTopicEpam());
        assertEquals(givenLecture.getDevice(), currentLecture.getDevice());
        assertEquals(givenLecture.getLinkYoutube(), currentLecture.getLinkYoutube());
        assertEquals(givenLecture.getName(), currentLecture.getName());
        assertEquals(givenLecture.getLinkVideoPortalEpam(), currentLecture.getLinkVideoPortalEpam());
        assertEquals(givenTopic.getId(), currentTopic.getId());
        assertEquals(givenTopic.getLinkToDescription(), currentTopic.getLinkToDescription());
        assertEquals(givenTopic.getName(), currentTopic.getName());
    }

    @Test
    public void should_return_null_if_lecture_not_exist() {
        //GIVEN
        long topicId = Long.MAX_VALUE;
        //WHEN
        Lecture lecture = lectureRepository.findLectureByTopicId(topicId);
        //THEN
        assertNull("Expected NULL", lecture);
    }
}