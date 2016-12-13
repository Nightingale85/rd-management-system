package com.epam.rd.frontend.controller.admin;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.frontend.service.LectureService;
import com.epam.rd.frontend.service.PracticalTaskService;
import com.epam.rd.frontend.service.TopicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TopicControllerTest {
    @Value("${url.domain}")
    private String domainUrl;
    private MockMvc mockMvc;

    private TopicDto topicDto;
    private PracticalTaskDto practicalTaskDto;
    private LectureDto lectureDto;


    @Mock
    private TopicService topicService;
    @Mock
    private LectureService lectureService;
    @Mock
    private PracticalTaskService practicalTaskService;


    @Before
    public void set_up() throws JsonProcessingException {
        TopicController controller = new TopicController(topicService, lectureService, practicalTaskService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        topicDto = new TopicDto();
        lectureDto = new LectureDto();
        practicalTaskDto = new PracticalTaskDto();

        topicDto.setId(1L);
        topicDto.setModuleId(1L);
        topicDto.setTitle("Test Title");
        topicDto.setDescription("Description");
        topicDto.setPracticalTaskId(1L);
        topicDto.setLectureId(1L);

        practicalTaskDto.setId(2L);
        practicalTaskDto.setTopicId(topicDto.getId());

        lectureDto.setId(2L);
        lectureDto.setTopicId(topicDto.getId());


    }

    @Test
    public void should_make_view_page() throws Exception {
        //GIVEN
        final String urlTemplate = "/admin/topic/1";
        when(topicService.getTopicById(anyLong())).thenReturn(topicDto);
        when(lectureService.getLectureDtoByTopicId(anyLong())).thenReturn(lectureDto);
        when(practicalTaskService.getPracticalTaskDtoByTopicId(anyLong())).thenReturn(practicalTaskDto);

        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));

        //THEN
        verify(topicService, times(1)).getTopicById(anyLong());
        verify(practicalTaskService, times(1)).getPracticalTaskDtoByTopicId(anyLong());
        verify(lectureService, times(1)).getLectureDtoByTopicId(anyLong());
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("topic"))
                .andExpect(model().attribute("task", practicalTaskDto))
                .andExpect(model().attribute("lecture", lectureDto));

    }


    @Test
    public void should_redirect_after_update_topic() throws Exception {
        //GIVEN
        final String urlTemplate = "/admin/topic/edit";
        TopicDto newTopic = new TopicDto();
        when(topicService.updateTopic(anyObject())).thenReturn(newTopic);

        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate));

        //THEN
        verify(topicService, times(1)).updateTopic(anyObject());
        resultActions.andExpect(status().is3xxRedirection());
    }


}