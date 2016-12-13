package com.epam.rd.frontend.controller;

import com.epam.rd.dto.ProgramDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Sergiy_Solovyov
 */

@Controller
public class DashboardController {

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping(value = {"/", "/dashboard"}, method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        List<ProgramDto> programDtos = restTemplate.getForObject("/programs", java.util.ArrayList.class);
        model.addAttribute("programs", programDtos);
        return "info/main_dashboard";
    }
}
