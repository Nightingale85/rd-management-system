package com.epam.rd.frontend.controller.info;

import com.epam.rd.frontend.model.ProgramInfo;
import com.epam.rd.frontend.model.ProgramTitles;
import com.epam.rd.frontend.service.info.ProgramInfoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "/info")
public class ProgramInfoController {
    private ProgramInfoFacade programInfoFacade;

    @Autowired
    public ProgramInfoController(ProgramInfoFacade programInfoFacade) {
        this.programInfoFacade = programInfoFacade;
    }

    @RequestMapping(value = "/dashboard", method = GET)
    public ModelAndView getProgramList() {
        ModelAndView modelAndView = new ModelAndView("info/dashboard_info");
        ProgramTitles programTitles = programInfoFacade.getProgramsInfo();
        modelAndView.addObject("programs", programTitles);
        return modelAndView;
    }

    @RequestMapping(value = "/program", method = GET)
    public ModelAndView getProgramById(@RequestParam("programId") Long programId) {
        ModelAndView modelAndView = new ModelAndView("info/program_info_new");
        ProgramInfo programInfo = programInfoFacade.getProgramInfoByProgramId(programId);
        modelAndView.addObject("programInfo", programInfo);
        return modelAndView;
    }

    @RequestMapping(value = "/program/{programId}/module/{moduleId}", method = GET)
    public ModelAndView getTopicsByModuleId(@PathVariable("moduleId") Long moduleId,
                                            @PathVariable("programId") Long programId) {
        ModelAndView modelAndView = new ModelAndView("info/program_info_new");
        ProgramInfo programInfo = programInfoFacade.getProgramInfoByProgramIdModuleId(programId, moduleId);
        modelAndView.addObject("programInfo", programInfo);
        modelAndView.addObject("moduleTitle", programInfo.getModules().get(moduleId));
        return modelAndView;
    }
}
