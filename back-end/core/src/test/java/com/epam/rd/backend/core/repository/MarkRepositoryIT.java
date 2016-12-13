package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.TempDataFactory;
import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.model.Mark;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Sergiy_Solovyov
 */
@ContextConfiguration(classes = SpringCoreConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(value = "classpath:app-config-test.properties")
@Transactional
public class MarkRepositoryIT {

    @Autowired
    private MarkRepository markRepository;
    private Mark mark;
    private static final String MENTEE_EMAIL = "mentee@email.com";
    private static final String MENTOR_EMAIL = "mentor@email.com";
    private static final Long ID = 1L;

    @Before
    public void init() {
        mark = TempDataFactory.getMark();
    }
    @Test
    public void should_return_list_of_marks_by_mentee_email() {
        //GIVEN
        markRepository.save(mark);
        //WHEN
        List<Mark> marks = markRepository.findMarksByMenteeEmail(MENTEE_EMAIL);
        //THEN
        assertTrue(marks.size() == 1);
    }

    @Test
    public void should_return_list_of_marks_by_estimator_email_and_task_id() {
        //GIVEN
        markRepository.save(mark);
        //WHEN
        List<Mark> marks = markRepository.findMarksByPracticalTaskIdAndEstimatorEmail(ID, MENTOR_EMAIL);
        //THEN
        assertTrue(marks.size() == 1);
    }
}
