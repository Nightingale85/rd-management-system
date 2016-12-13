package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.dto.TopicDto;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Olga_Kramska on 9/19/2016.
 */
public class TopicDtoConverterTest {
    private AbstractConverter<Topic, TopicDto> converter = new TopicDtoConverter();

    @Test
    public void should_convert_topic_list_to_dto_list() {
        //GIVEN
        Module module = new Module();
        module.setId(1L);
        module.setName("Module 1");

        Topic topic1 = new Topic();
        topic1.setId(1L);
        topic1.setName("Topic 1");
        topic1.setModule(module);

        Topic topic2 = new Topic();
        topic2.setId(2L);
        topic2.setName("Topic 2");
        topic2.setModule(module);

        List<Topic> topicList = Arrays.asList(topic1, topic2);
        //WHEN
        List<TopicDto> dtoList = converter.convertList(topicList);
        //THEN
        assertEquals(topicList.size(), dtoList.size());
        assertEquals(topicList.get(0).getId(), dtoList.get(0).getId());
        assertEquals(topicList.get(0).getName(), dtoList.get(0).getTitle());
        assertEquals(topicList.get(0).getModule().getId(), dtoList.get(0).getModuleId());

        assertEquals(topicList.get(1).getId(), dtoList.get(1).getId());
        assertEquals(topicList.get(1).getName(), dtoList.get(1).getTitle());
        assertEquals(topicList.get(1).getModule().getId(), dtoList.get(1).getModuleId());
    }
}
