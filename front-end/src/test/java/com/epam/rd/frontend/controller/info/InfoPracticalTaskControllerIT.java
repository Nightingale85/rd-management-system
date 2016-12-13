package com.epam.rd.frontend.controller.info;

import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.frontend.controller.InfoPracticalTaskController;
import com.epam.rd.frontend.service.PracticalTaskService;
import org.junit.Assert;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Sergiy_Solovyov
 */
@RunWith(MockitoJUnitRunner.class)
public class InfoPracticalTaskControllerIT {

    private MockMvc mockMvc;
    private PracticalTaskDto practicalTaskDto;

    @Mock
    private PracticalTaskService practicalTaskService;


    @Before
    public void set_up() {
        mockMvc = MockMvcBuilders.standaloneSetup(new InfoPracticalTaskController(
                practicalTaskService)).build();
        practicalTaskDto = new PracticalTaskDto();
        practicalTaskDto.setTitle("Homework 1");
        practicalTaskDto.setDescription("Description");
        practicalTaskDto.setScoreSystem("Score system");
    }

    @Test
    public void index_test() throws Exception {
        //GIVEN
        final String urlTemplate = "/info/task/1";
        when(practicalTaskService.getPracticalTaskDtoById(1L)).thenReturn(practicalTaskDto);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(forwardedUrl("info/practical_task_info"))
                     .andExpect(view().name("info/practical_task_info"))
                     .andExpect(model().attributeExists("task"));
        Assert.assertEquals(true, resultActions.andReturn().getModelAndView().getModel().containsKey("task"));
    }
}

