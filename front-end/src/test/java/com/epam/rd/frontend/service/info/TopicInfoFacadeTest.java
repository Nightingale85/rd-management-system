package com.epam.rd.frontend.service.info;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.frontend.converter.TopicInfoConverter;
import com.epam.rd.frontend.model.TopicInfo;
import com.epam.rd.frontend.service.LectureService;
import com.epam.rd.frontend.service.PracticalTaskService;
import com.epam.rd.frontend.service.TopicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TopicInfoFacadeTest {
    private static final long ONE = 1L;
    private static final long TWO = 2L;
    private static final long THREE = 3L;
    private static final Long MODULE_ID = ONE;

    @Mock
    private TopicService topicServiceMock;

    @Mock
    private LectureService lectureServiceMock;

    @Mock
    private PracticalTaskService taskServiceMock;

    @Mock
    private TopicInfoConverter converterMock;

    private TopicInfoFacade topicInfoFacade;

    @Before
    public void set_up() {
        topicInfoFacade = new TopicInfoFacade(topicServiceMock, lectureServiceMock, taskServiceMock, converterMock);
    }

    @Test
    public void should_return_topic_info_list_by_module_id() {
        //GIVEN
        int count = 3;
        List<TopicDto> topicDtoList = getTopicDtoList(count);
        List<TopicInfo> expectedTopicInfoList = getTopicInfoList();
        LectureDto lectureDtoOne = getLectureDto(ONE);
        LectureDto lectureDtoTwo = getLectureDto(TWO);
        LectureDto lectureDtoThree = getLectureDto(THREE);
        PracticalTaskDto taskDtoOne = getTaskDto(ONE);
        PracticalTaskDto taskDtoTwo = getTaskDto(TWO);
        PracticalTaskDto taskDtoThree = getTaskDto(THREE);
        when(topicServiceMock.getTopicsByModuleId(MODULE_ID)).thenReturn(topicDtoList);
        when(lectureServiceMock.getLectureDtoByTopicId(ONE)).thenReturn(lectureDtoOne);
        when(lectureServiceMock.getLectureDtoByTopicId(TWO)).thenReturn(lectureDtoTwo);
        when(lectureServiceMock.getLectureDtoByTopicId(THREE)).thenReturn(lectureDtoThree);
        when(taskServiceMock.getPracticalTaskDtoByTopicId(ONE)).thenReturn(taskDtoOne);
        when(taskServiceMock.getPracticalTaskDtoByTopicId(TWO)).thenReturn(taskDtoTwo);
        when(taskServiceMock.getPracticalTaskDtoByTopicId(THREE)).thenReturn(taskDtoThree);
        when(converterMock.convert(getTopicDto(ONE), lectureDtoOne, taskDtoOne)).thenReturn(getTopicInfo(ONE));
        when(converterMock.convert(getTopicDto(TWO), lectureDtoTwo, taskDtoTwo)).thenReturn(getTopicInfo(TWO));
        when(converterMock.convert(getTopicDto(THREE), lectureDtoThree, taskDtoThree)).thenReturn(getTopicInfo(THREE));
        //WHEN
        List<TopicInfo> actualTopicInfoList = topicInfoFacade.getTopicInfoListByModuleId(MODULE_ID);
        //THEN
        assertEquals(expectedTopicInfoList, actualTopicInfoList);
        verify(topicServiceMock, times(1)).getTopicsByModuleId(MODULE_ID);
        verify(lectureServiceMock, times(1)).getLectureDtoByTopicId(ONE);
        verify(lectureServiceMock, times(1)).getLectureDtoByTopicId(TWO);
        verify(lectureServiceMock, times(1)).getLectureDtoByTopicId(THREE);
        verify(taskServiceMock, times(1)).getPracticalTaskDtoByTopicId(ONE);
        verify(taskServiceMock, times(1)).getPracticalTaskDtoByTopicId(TWO);
        verify(taskServiceMock, times(1)).getPracticalTaskDtoByTopicId(THREE);
        verify(converterMock, times(1)).convert(getTopicDto(ONE), lectureDtoOne, taskDtoOne);
        verify(converterMock, times(1)).convert(getTopicDto(TWO), lectureDtoTwo, taskDtoTwo);
        verify(converterMock, times(1)).convert(getTopicDto(THREE), lectureDtoThree, taskDtoThree);
        verifyNoMoreInteractions(topicServiceMock, lectureServiceMock, taskServiceMock, converterMock);
    }

    @SuppressWarnings("checkstyle:methodname")
    private List<TopicDto> getTopicDtoList(int count) {
        List<TopicDto> topicDtoList = new ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            long id = index + 1L;
            TopicDto topicDto = getTopicDto(id);
            topicDtoList.add(topicDto);
        }
        return topicDtoList;
    }

    @SuppressWarnings("checkstyle:methodname")
    private TopicDto getTopicDto(long id) {
        TopicDto topicDto = new TopicDto();
        topicDto.setId(id);
        topicDto.setTitle(String.format("Topic #%d", id));
        topicDto.setModuleId(MODULE_ID);
        topicDto.setLectureId(id);
        topicDto.setPracticalTaskId(id);
        return topicDto;
    }

    @SuppressWarnings("checkstyle:methodname")
    private PracticalTaskDto getTaskDto(long id) {
        PracticalTaskDto taskDto = new PracticalTaskDto();
        taskDto.setId(id);
        taskDto.setTitle(String.format("Practical task #%d", id));
        return taskDto;
    }

    @SuppressWarnings("checkstyle:methodname")
    private LectureDto getLectureDto(long id) {
        LectureDto lectureDto = new LectureDto();
        lectureDto.setId(id);
        lectureDto.setTitle(String.format("Lecture #%d", id));
        return lectureDto;
    }

    @SuppressWarnings("checkstyle:methodname")
    private List<TopicInfo> getTopicInfoList() {
        List<TopicInfo> topicInfoList = new ArrayList<>();
        topicInfoList.add(getTopicInfo(ONE));
        topicInfoList.add(getTopicInfo(TWO));
        topicInfoList.add(getTopicInfo(THREE));
        return topicInfoList;
    }

    @SuppressWarnings("checkstyle:methodname")
    private TopicInfo getTopicInfo(long topicId) {
        return new TopicInfo(topicId,
                String.format("Topic #%d", topicId),
                String.format("Lecture #%d", topicId),
                String.format("Practical task #%d", topicId));
    }
}