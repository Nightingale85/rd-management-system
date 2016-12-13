package com.epam.rd.frontend.controller;

import com.epam.rd.frontend.service.ProgramInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/program/info")
public class InfoProgramController {

    @Autowired
    private ProgramInfoService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String printWelcome(Model model, @PathVariable("id") final Long programId) {
        model.addAttribute("programInfo", service.getProgramInfoByProgramId(programId));
        return "info/program_info";
    }

    @RequestMapping(value = "/module/{id}", method = RequestMethod.GET)
    public String printCurrentModule(Model model, @PathVariable("id") final Long currentModuleId) {
        model.addAttribute("programInfo", service.getProgramInfoByModuleId(currentModuleId));
        return "info/program_info";
    }

}