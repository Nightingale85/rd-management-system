package com.epam.rd.frontend.controller;

import com.epam.rd.dto.ModuleDtoCreate;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;
import com.epam.rd.frontend.component.ModuleWithTopics;
import com.epam.rd.frontend.service.ModuleService;
import com.epam.rd.frontend.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private ProgramService programService;
    private ModuleService moduleService;

    @Autowired
    public AdminController(ModuleService moduleService, ProgramService programService) {
        this.moduleService = moduleService;
        this.programService = programService;
    }

    @RequestMapping(value = "/dashboard", method = GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("admin/dashboard");
        ProgramDtoCreate programDtoCreate = new ProgramDtoCreate();
        programDtoCreate.setStartDate(LocalDate.now());
        programDtoCreate.setEndDate(LocalDate.now());
        modelAndView.addObject("programs", programService.getAllPrograms());
        modelAndView.addObject("newProgram", programDtoCreate);
        return modelAndView;
    }

    @RequestMapping(value = "/new_program", method = POST)
    public String addProgram(@ModelAttribute(value = "newProgram") ProgramDtoCreate dtoCreate) {
        programService.addProgram(dtoCreate);
        return "redirect:dashboard";
    }

    @RequestMapping(value = "/program", method = GET)
    public ModelAndView adminProgramView(Model model) {
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/edit/program/{programId}", method = RequestMethod.GET)
    public String programById(@PathVariable Long programId, Model model) {
        ProgramDto programDto = programService.getProgramById(programId);
        List<ModuleWithTopics> moduleWithTopics = moduleService.getModulesWithTopics(programDto.getId());
        model.addAttribute("programDto", programDto);
        model.addAttribute("moduleDtoCreate", new ModuleDtoCreate());
        model.addAttribute("moduleWithTopics", moduleWithTopics);
        return "admin/edit_program";
    }
    @RequestMapping(value = "/update_program", method = RequestMethod.POST)
    public String updateProgram(@ModelAttribute(value = "programDto") ProgramDto programDto) {
        programService.updateProgram(programDto);
        return "redirect:/rd/admin/edit/program/" + programDto.getId();
    }
    @RequestMapping(value = "/add_module", method = RequestMethod.POST)
    public String createModule(
            @ModelAttribute(value = "programDto") ProgramDto programDto,
            @ModelAttribute(value = "moduleDtoCreate") ModuleDtoCreate moduleDtoCreate) {
        moduleDtoCreate.setProgramId(programDto.getId());
        moduleService.addModule(moduleDtoCreate);
        return "redirect:/rd/admin/edit/program/" + programDto.getId();
    }
    @RequestMapping(value = "/delete_module/{moduleId}", method = RequestMethod.POST)
    public String deleteModule(@PathVariable Long moduleId,
                               @ModelAttribute(value = "programDto") ProgramDto programDto) {
        moduleService.deleteModuleById(moduleId);
        return "redirect:/rd/admin/edit/program/" + programDto.getId();
    }
}
