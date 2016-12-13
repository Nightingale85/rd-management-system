package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.service.PracticalTaskService;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreConfig.class)
@TestPropertySource(value = "classpath:app-config-test.properties")
public class PracticalTaskServiceImplTest {
    private static final String TITLE = "PracticalTask";
    private static final String DESCRIPTION = "Description";
    @Autowired
    private PracticalTaskService service;

    private PracticalTaskDtoCreate dtoCreate;

    @Before()
    public void init() {
        dtoCreate = new PracticalTaskDtoCreate();
        dtoCreate.setTitle(TITLE);
        dtoCreate.setDescription(DESCRIPTION);
    }

    @Ignore("Should prepare database before running test")
    @Test
    public void should_create_and_read_practical_pask() throws Exception {
        PracticalTaskDto dto = service.createPracticalTask(dtoCreate);
        assertNotNull(dto.getId());
        assertEquals(TITLE, dto.getTitle());
        assertEquals(DESCRIPTION, dto.getDescription());
    }

    @Ignore("Should prepare database before running test")
    @Test
    public void should_return_list_practical_task() {
        service.createPracticalTask(dtoCreate);
        assertFalse(service.getAllPracticalTask().isEmpty());
    }

    @Ignore
    @Test
    public void should_save_and_read_practical_task_by_id() {
        PracticalTaskDto dto = service.createPracticalTask(dtoCreate);
        assertNotNull(dto);
    }

    @Ignore("Should prepare database before running test")
    @Test
    public void should_update_practical_task() {
        PracticalTaskDto dto = service.createPracticalTask(dtoCreate);
        dto.setTitle("New Name");
        PracticalTaskDto dtoUpdate = service.updatePracticalTask(dto);
        assertEquals("New Name", dtoUpdate.getTitle());
    }

    @Ignore("Should prepare database before running test")
    @Test(expected = EntityDoesNotExistException.class)
    public void should_delete_practical_task_by_id() {
        PracticalTaskDto dto = service.createPracticalTask(dtoCreate);
        service.deletePracticalTaskById(dto.getId());
        service.deletePracticalTaskById(dto.getId());
    }

}