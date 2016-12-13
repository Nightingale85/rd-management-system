package com.epam.rd.frontend.controller.admin;

import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.PracticalTaskDtoCreate;
import com.epam.rd.frontend.service.PracticalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/admin/task")
public class PracticalTaskController {
    private PracticalTaskService service;

    @Autowired
    public PracticalTaskController(PracticalTaskService service) {
        this.service = service;
    }

    @RequestMapping(value = "/add", method = GET)
    public ModelAndView addPracticalTask(@RequestParam("topicId") Long topicId) {
        ModelAndView modelAndView = new ModelAndView("admin/new_task");
        PracticalTaskDtoCreate dtoCreate = new PracticalTaskDtoCreate();
        dtoCreate.setTopicId(topicId);
        modelAndView.addObject("task", dtoCreate);
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = POST)
    public String createPracticalTask(@ModelAttribute("task") PracticalTaskDtoCreate dtoCreate) {
        service.addPracticalTask(dtoCreate);
        return "redirect:/rd/admin/topic/" + dtoCreate.getTopicId();
    }

    @RequestMapping(value = "/edit", method = GET)
    public ModelAndView editPracticalTask(@RequestParam("topicId") Long topicId) {
        ModelAndView modelAndView = new ModelAndView("admin/edit_task");
        PracticalTaskDto taskDto = service.getPracticalTaskDtoByTopicId(topicId);
        modelAndView.addObject("task", taskDto);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = POST)
    public String updatePracticalTask(@ModelAttribute("task") PracticalTaskDto dto) {
        PracticalTaskDto taskDto = service.updatePracticalTask(dto);
        return  "redirect:/rd/admin/topic/" + taskDto.getTopicId();
    }
}
