package com.epam.rd.frontend.controller.info;

import com.epam.rd.dto.ProgramDto;
import com.epam.rd.frontend.controller.DashboardController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Sergiy_Solovyov
 */
@RunWith(MockitoJUnitRunner.class)
public class DashboardControllerIT {

    private MockMvc mockMvc;
    private ArrayList<ProgramDto> programDtos;
    private ProgramDto programDto;
    @InjectMocks
    private DashboardController dashboardControllerMock;
    @Mock
    private RestTemplate restTemplateMock;

    @Before
    public void set_up() {
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardControllerMock).build();
        programDto = new ProgramDto();
        programDto.setTitle("name");
        programDto.setDescription("Description");
        programDtos = new ArrayList<>();
        programDtos.add(programDto);
    }

    @Test
    public void index_test() throws Exception {
        //GIVEN
        final String urlTemplate = "/";
        when(restTemplateMock.getForObject("/", java.util.ArrayList.class)).thenReturn(programDtos);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(forwardedUrl("info/main_dashboard"))
                .andExpect(view().name("info/main_dashboard"));
        Assert.assertEquals(true, resultActions.andReturn().getModelAndView().getModel().containsKey("programs"));

    }
    @Test
    public void index_dashboard_test() throws Exception {
        //GIVEN
        final String urlTemplate = "/dashboard";
        when(restTemplateMock.getForObject("/dashboard", java.util.ArrayList.class)).thenReturn(programDtos);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(forwardedUrl("info/main_dashboard"))
                .andExpect(view().name("info/main_dashboard"));
        Assert.assertEquals(true, resultActions.andReturn().getModelAndView().getModel().containsKey("programs"));
    }
}
