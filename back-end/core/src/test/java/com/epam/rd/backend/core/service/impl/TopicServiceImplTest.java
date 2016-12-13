package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.TopicDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.backend.core.repository.ModuleRepository;
import com.epam.rd.backend.core.repository.TopicRepository;
import com.epam.rd.backend.core.service.TopicService;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Olga_Kramska
 * @author Oleksii Ushakov
 */
@RunWith(MockitoJUnitRunner.class)
public class TopicServiceImplTest {
    private static final Long ID = 999L;

    @Mock
    private TopicRepository repositoryMock;

    @Mock
    private ModuleRepository moduleRepositoryMock;

    @Mock
    private TopicDtoConverter converterMock;

    private static Topic topic;
    private static TopicDto topicDto;
    private static TopicDtoCreate topicDtoCreate;
    private static Module module;

    private TopicService service;

    @Before
    public void set_up() throws Exception {
        service = new TopicServiceImpl(repositoryMock, moduleRepositoryMock, converterMock);

        topicDto = new TopicDto();
        topicDto.setModuleId(ID);

        topic = new Topic();
        topic.setId(ID);
        topic.setModule(moduleRepositoryMock.getOne(topicDto.getModuleId()));

        topicDtoCreate = new TopicDtoCreate();

        module = new Module();
    }

    @Test
    public void should_create_topic() {
        //GIVEN
        when(converterMock.convert(topicDtoCreate)).thenReturn(topic);
        when(repositoryMock.save(topic)).thenReturn(topic);
        when(converterMock.convert(topic)).thenReturn(topicDto);
        when(moduleRepositoryMock.findOne(anyLong())).thenReturn(module);

        //WHEN
        TopicDto testTopicDto = service.createTopic(topicDtoCreate);

        //THEN
        verify(converterMock).convert(topicDtoCreate);
        verify(repositoryMock).save(TopicServiceImplTest.topic);
        verify(moduleRepositoryMock).getOne(TopicServiceImplTest.ID);
        verify(converterMock).convert(TopicServiceImplTest.topic);
        verifyNoMoreInteractions(converterMock, repositoryMock);

        assertEquals(testTopicDto, topicDto);
    }

    @Test
    public void should_update_topic() {
        //GIVEN
        when(converterMock.convert(topicDto)).thenReturn(topic);
        when(repositoryMock.findOne(ID)).thenReturn(topic);
        when(repositoryMock.save(topic)).thenReturn(topic);
        when(converterMock.convert(topic)).thenReturn(topicDto);

        //WHEN
        TopicDto testTopicDto = service.updateTopic(topicDto);

        //THEN
        verify(converterMock).convert(topicDto);
        verify(repositoryMock).findOne(ID);
        verify(repositoryMock).save(topic);
        verify(converterMock).convert(topic);
        verifyNoMoreInteractions(converterMock, repositoryMock);

        assertEquals(testTopicDto, topicDto);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_throw_exception_in_update_topic() {
        //GIVEN
        when(converterMock.convert(topicDto)).thenReturn(topic);

        //WHEN
        service.updateTopic(topicDto);

        //THEN
        verify(converterMock).convert(topicDto);
        verify(repositoryMock).findOne(ID);
        verifyNoMoreInteractions(converterMock, repositoryMock);
    }

    @Test
    public void should_get_all_topic() {
        //GIVEN
        List<Topic> topicList = Collections.singletonList(topic);
        List<TopicDto> topicDtoList = Collections.singletonList(topicDto);

        when(repositoryMock.findAll()).thenReturn(topicList);
        when(converterMock.convertList(topicList)).thenReturn(topicDtoList);

        //WHEN
        List<TopicDto> testAllTopics = service.getAllTopic();

        //THEN
        verify(repositoryMock).findAll();
        verify(converterMock).convertList(topicList);
        verifyNoMoreInteractions(converterMock, repositoryMock);

        assertEquals(testAllTopics, Collections.singletonList(topicDto));
    }

    @Test
    public void should_get_topics_by_module_id() {
        //GIVEN
        List<Topic> topicList = Collections.singletonList(topic);
        List<TopicDto> topicDtoList = Collections.singletonList(topicDto);

        when(repositoryMock.findTopicsByModuleId(ID)).thenReturn(topicList);
        when(converterMock.convertList(topicList)).thenReturn(topicDtoList);

        //WHEN
        List<TopicDto> testAllTopics = service.getTopicsByModuleId(ID);

        //THEN
        verify(repositoryMock).findTopicsByModuleId(ID);
        verify(converterMock).convertList(topicList);
        verifyNoMoreInteractions(converterMock, repositoryMock);

        assertEquals(testAllTopics, Collections.singletonList(topicDto));
    }

    @Test
    public void should_get_topic_by_id() {
        //GIVEN
        when(repositoryMock.findOne(ID)).thenReturn(topic);
        when(converterMock.convert(topic)).thenReturn(topicDto);

        //WHEN
        TopicDto testTopicDto = service.getTopicById(ID);

        //THEN
        verify(repositoryMock).findOne(ID);
        verify(converterMock).convert(topic);
        verifyNoMoreInteractions(converterMock, repositoryMock);

        assertEquals(testTopicDto, topicDto);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_throw_exception_in_get_topic_by_id() {
        //WHEN
        service.getTopicById(ID);

        //THEN
        verify(repositoryMock).findOne(ID);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void should_delete_topic_by_id() {
        //GIVEN
        when(repositoryMock.findOne(ID)).thenReturn(topic);

        //WHEN
        service.deleteTopicById(ID);

        //THEN
        verify(repositoryMock).findOne(ID);
        verify(repositoryMock).delete(ID);
        verifyNoMoreInteractions(converterMock, repositoryMock);
    }
}
