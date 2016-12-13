package com.epam.rd.backend.core.repository;


import com.epam.rd.backend.core.TempDataFactory;
import com.epam.rd.backend.core.config.SpringCoreConfig;
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
import static org.junit.Assert.*;


@ContextConfiguration(classes = SpringCoreConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(value = "classpath:app-config-test.properties")
@Transactional
public class PracticalTaskRepositoryIT {

    @Autowired
    private PracticalTaskRepository practicalTaskRepository;
    @Autowired
    private TopicRepository topicRepository;

    private PracticalTask practicalTask;
    private Topic topic;

    @Before
    public void init() {
        practicalTask = TempDataFactory.getPracticalTask();
        topic = TempDataFactory.getTopic();
    }

    @Test
    public void should_create_practical_task() {
        //GIVEN
        topic.setPracticalTask(practicalTask);
        topicRepository.save(topic);
        //WHEN
        PracticalTask practicalTaskDB = practicalTaskRepository.findOne(practicalTask.getId());
        Topic currentTopic = topicRepository.findOne(topic.getId());
        //THEN
        assertEquals(practicalTask.getName(), practicalTaskDB.getName());
        assertEquals(practicalTask.getLinkToDescription(), practicalTaskDB.getLinkToDescription());
        assertEquals(practicalTask.getDateEnd(), practicalTaskDB.getDateEnd());
        assertEquals(topic.getId(), currentTopic.getId());
        assertEquals(topic.getLinkToDescription(), currentTopic.getLinkToDescription());
        assertEquals(topic.getName(), currentTopic.getName());
    }

    @Test
    public void should_delete_practical_task() {
        //GIVEN
        practicalTaskRepository.save(practicalTask);
        //WHEN
        practicalTaskRepository.delete(practicalTask.getId());
        //THEN
        assertNull(practicalTaskRepository.findOne(practicalTask.getId()));
    }

    @Test
    public void should_update_practical_task() {
        //GIVEN
        practicalTaskRepository.save(practicalTask);
        String newName = "New Name";
        practicalTask.setName(newName);
        //WHEN
        practicalTaskRepository.save(practicalTask);
        //THEN
        assertEquals(newName, practicalTaskRepository.findOne(practicalTask.getId()).getName());
    }
}