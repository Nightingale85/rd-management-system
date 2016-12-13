package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceImplTest {
    private static final Long ID = 1L;
    private List<TopicDto> topics;
    private TopicDto topic;

    @Mock
    private RestOperations restOperationsMock;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Before
    public void set_up() {
        topics = new ArrayList<>();
        topic = new TopicDto();
        topic.setTitle("topicTitle");
        topic.setDescription("topicDescription");
        topic.setId(ID);
        topic.setModuleId(ID);
        topics.add(topic);
    }

    @Test
    public void should_return_topic_by_id() {
        //GIVEN
        when(restOperationsMock.getForObject(anyString(), eq(TopicDto.class), anyLong())).thenReturn(topic);

        //WHEN
        TopicDto topicDto = topicService.getTopicById(ID);

        //THEN
        verify(restOperationsMock).getForObject(anyString(), eq(TopicDto.class), anyLong());
        verifyNoMoreInteractions(restOperationsMock);
        assertEquals(topic, topicDto);
    }


    @Test
    public void should_return_topic_dto_after_update_topic() throws JsonProcessingException {
        //GIVEN
        String urlTemplate = "/topic/{id}";
        TopicDto dto = new TopicDto();
        dto.setId(1L);
        ResponseEntity<TopicDto> responseEntity = ResponseEntity.ok(dto);
        HttpEntity<TopicDto> httpEntity = new HttpEntity<>(dto);
        when(restOperationsMock.exchange(eq(urlTemplate), eq(PUT), eq(httpEntity), eq(TopicDto.class),
                                         eq(dto.getId())))
                .thenReturn(responseEntity);
        //WHEN
        TopicDto taskDto = topicService.updateTopic(dto);
        //THEN
        assertSame(dto, taskDto);
        verify(restOperationsMock, times(1))
                .exchange(eq(urlTemplate), eq(PUT), eq(httpEntity), eq(TopicDto.class), eq(dto.getId()));
        verifyNoMoreInteractions(restOperationsMock);
    }

    @Test
    public void should_return_topic_id_after_create_new_topic() {
        //GIVEN
        String urlTemplate = "/topic";
        Long expectedId = 1L;
        TopicDtoCreate dtoCreate = new TopicDtoCreate();
        HttpEntity<TopicDtoCreate> httpEntity = new HttpEntity<>(dtoCreate);
        TopicDto topicDto = new TopicDto();
        topicDto.setId(expectedId);
        ResponseEntity<TopicDto> responseEntity = new ResponseEntity<>(topicDto, CREATED);
        when(restOperationsMock.exchange(eq(urlTemplate), eq(POST), eq(httpEntity), eq(TopicDto.class)))
                .thenReturn(responseEntity);
        //WHEN
        Long actualId = topicService.addTopic(dtoCreate);
        //THEN
        assertEquals(expectedId, actualId);
        verify(restOperationsMock, times(1)).exchange(eq(urlTemplate), eq(POST), eq(httpEntity), eq(TopicDto.class));
        verifyNoMoreInteractions(restOperationsMock);
    }

    @Test
    public void should_return_list_topics_by_module_id() {
        //GIVEN
        String urlTemplate = "/module/{moduleId}/topics";
        Long moduleId = ID;
        ResponseEntity<List<TopicDto>> responseEntity = ResponseEntity.ok(topics);
        when(restOperationsMock.exchange(eq(urlTemplate), eq(GET), eq(null),
                                         eq(new ParameterizedTypeReference<List<TopicDto>>() {
                                         }), eq(moduleId))).thenReturn(responseEntity);

        //WHEN
        topicService.getTopicsByModuleId(ID);

        //THEN
        verify(restOperationsMock).exchange(eq(urlTemplate), eq(GET), eq(null),
                                            eq(new ParameterizedTypeReference<List<TopicDto>>() {
                                            }), eq(moduleId));
        verifyNoMoreInteractions(restOperationsMock);

    }

}