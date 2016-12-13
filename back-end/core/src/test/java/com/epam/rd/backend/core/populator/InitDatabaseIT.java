package com.epam.rd.backend.core.populator;

import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.config.TemporaryDataPopulationConfig;
import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Program;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.backend.core.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("tmp-data")
@ContextConfiguration(classes = {SpringCoreConfig.class, TemporaryDataPopulationConfig.class})
@TestPropertySource(value = "classpath:app-config-test.properties")
public class InitDatabaseIT {
    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private PracticalTaskRepository taskRepository;

    @Test
    @Transactional
    public void should_return_true_if_program_include_modules() {
        //GIVEN
        final long id = 1L;
        final Program program = programRepository.findOne(id);
        //WHEN
        final boolean emptyProgram = program.getModules().isEmpty();
        //THEN
        assertFalse(emptyProgram);
    }

    @Test
    @Transactional
    public void should_return_true_if_module_include_topic() {
        //GIVEN
        final List<Module> modules = moduleRepository.findAll();
        final Module moduleOne = modules.get(0);
        final Module moduleTwo = modules.get(1);
        //WHEN
        final boolean emptyModuleOne = moduleOne.getTopics().isEmpty();
        final boolean emptyModuleTwo = moduleTwo.getTopics().isEmpty();
        //THEN
        assertFalse("List of Topic in Module 1 is empty", emptyModuleOne);
        assertFalse("List of Topic in Module 2 is empty", emptyModuleTwo);
    }

    @Test
    public void should_return_true_if_each_topic_contains_lecture_and_task() {
        //GIVEN
        final Iterable<Topic> topics = topicRepository.findAll();
        //WHEN
        for (final Topic topic : topics) {
            //THEN
            assertNotNull(topic.getLecture());
            assertNotNull(topic.getPracticalTask());
        }
    }

    @Test
    public void should_return_true_if_count_of_lecture_equals_nine() {
        //WHEN
        long count = lectureRepository.count();
        //THEN
        assertTrue(count > 0);
    }

    @Test
    public void should_return_true_if_count_of_task_equals_nine() {
        //WHEN
        long count = topicRepository.count();
        //THEN
        assertTrue(count > 0);
    }
}