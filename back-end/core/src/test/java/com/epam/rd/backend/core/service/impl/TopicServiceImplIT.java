package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.repository.ModuleRepository;
import com.epam.rd.backend.core.service.TopicService;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;
import org.junit.Before;
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
public class TopicServiceImplIT {
    private static final String TITLE = "Topic";
    private static final String DESCRIPTION = "Description";

    @Autowired
    private TopicService service;

    private TopicDtoCreate dtoCreate;

    @Autowired
    private ModuleRepository repository;

    @Before
    public void init() {
        Module module = new Module();
        repository.save(module);
        dtoCreate = new TopicDtoCreate();
        dtoCreate.setTitle(TITLE);
        dtoCreate.setDescription(DESCRIPTION);
        dtoCreate.setModuleId(module.getId());
    }

    @Test
    public void should_create_and_read_topic() throws Exception {
        TopicDto newTopic = service.createTopic(dtoCreate);
        TopicDto topicFromDB = service.getTopicById(newTopic.getId());
        assertNotNull(topicFromDB.getId());
        assertEquals(TITLE, topicFromDB.getTitle());
        assertEquals(DESCRIPTION, topicFromDB.getDescription());
    }

    @Test
    public void should_return_list_topic() {
        service.createTopic(dtoCreate);
        assertFalse(service.getAllTopic().isEmpty());
    }

    @Test
    public void should_save_and_read_topic() {
        TopicDto dto = service.createTopic(dtoCreate);
        dto.setTitle("New Topic");
        TopicDto dtoUpdate = service.updateTopic(dto);
        assertEquals("New Topic", dtoUpdate.getTitle());
    }

    @Test
    public void should_create_and_delete_topic_by_name() {
        TopicDto dto = service.createTopic(dtoCreate);
        service.deleteTopicById(dto.getId());
    }

}