package com.epam.rd.frontend.controller.info;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.frontend.controller.InfoLectureController;
import com.epam.rd.frontend.service.LectureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(MockitoJUnitRunner.class)
public class InfoLectureIT {
    private static final long LECTURE_ID = 1L;
    private MockMvc mockMvc;
    private LectureDto lectureDto;

    @Mock
    private LectureService lectureService;

    @Before
    public void set_up() {
        mockMvc = MockMvcBuilders.standaloneSetup(new InfoLectureController(lectureService)).build();
        lectureDto = new LectureDto();
        lectureDto.setTitle("Name Lecture");
        lectureDto.setDescription("Description Lecture");
        lectureDto.setId(LECTURE_ID);
    }

    @Test
    public void should_return_model_attribute() throws Exception {
        //GIVEN
        final String urlTemplate = "/info/lecture/" + LECTURE_ID;

        when(lectureService.getLectureById(LECTURE_ID))
                .thenReturn(lectureDto);

        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));

        //THEN
        resultActions.andExpect(model().attributeExists("lecture"));

    }


}

