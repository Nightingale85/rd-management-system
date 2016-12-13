package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.TempDataFactory;
import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.model.Lecture;
import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.PracticalTask;
import com.epam.rd.backend.core.model.Topic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;


@ContextConfiguration(classes = SpringCoreConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(value = "classpath:app-config-test.properties")
@Transactional
public class TopicRepositoryIT {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private PracticalTaskRepository practicalTaskRepository;
    private Module module;
    private Topic topic;
    private PracticalTask practicalTask;

    private Lecture lecture;


    @Before
    public void init() {
        module = TempDataFactory.getModule();
        topic = TempDataFactory.getTopic();
        practicalTask = TempDataFactory.getPracticalTask();
        lecture = TempDataFactory.getLecture();
    }

    @Test
    public void should_create_topic() {
        //GIVEN
        practicalTaskRepository.save(practicalTask);
        lectureRepository.save(lecture);
        topic.setLecture(lecture);
        topic.setPracticalTask(practicalTask);
        //WHEN
        topicRepository.save(topic);
        Topic topicFromDB = topicRepository.findOne(topic.getId());
        //THEN
        assertNotNull(topicFromDB.getId());
        assertEquals(topic.getName(), topicFromDB.getName());
        assertEquals(lecture, topicFromDB.getLecture());
        assertEquals(practicalTask, topicFromDB.getPracticalTask());
    }

    @Test
    public void should_update_topic() {
        //GIVEN
        practicalTaskRepository.save(practicalTask);
        lectureRepository.save(lecture);
        topic.setLecture(lecture);
        topic.setPracticalTask(practicalTask);
        topicRepository.save(topic);
        String newLinkToDescriptionTopic = "New Topic Description";
        topic.setLinkToDescription(newLinkToDescriptionTopic);
        lecture.setName("New Lecture Name");
        topic.setLecture(lecture);
        //WHEN
        topicRepository.save(topic);
        Topic topicFromDB = topicRepository.findOne(topic.getId());
        //THEN
        assertEquals(newLinkToDescriptionTopic, topicFromDB.getLinkToDescription());
        assertEquals(lecture.getName(), topicFromDB.getLecture().getName());
    }

    @Test
    public void should_delete_topic() {
        //GIVEN
        topicRepository.save(topic);
        //WHEN
        topicRepository.delete(topic.getId());
        //GIVEN
        assertNull(topicRepository.findOne(topic.getId()));
    }

    @Test
    public void should_find_topic_by_module_id() {
        //GIVEN
        module.addTopic(topic);
        moduleRepository.save(module);
        topicRepository.save(topic);
        //WHEN
        List<Topic> topics = topicRepository.findTopicsByModuleId(module.getId());
        //GIVEN
        assertFalse(topics.isEmpty());
    }

}