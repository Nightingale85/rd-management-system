package com.epam.rd.frontend.controller;

import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.frontend.service.PracticalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author Sergiy_Solovyov
 */

@Controller
public class InfoPracticalTaskController {

    @Autowired
    private PracticalTaskService practicalTaskService;

    public InfoPracticalTaskController(PracticalTaskService practicalTaskService) {
        this.practicalTaskService = practicalTaskService;
    }

    @RequestMapping(value = "/info/task/{id}", method = RequestMethod.GET)
    public String index(@PathVariable Long id, Model model) {
        PracticalTaskDto task = practicalTaskService.getPracticalTaskDtoById(id);
        model.addAttribute("task", task);
        return "info/practical_task_info";
    }

    @RequestMapping(value = "/info/topic/task/{id}", method = RequestMethod.GET)
    public String practicalTaskByTopicId(@PathVariable Long id, Model model) {
        PracticalTaskDto task = practicalTaskService.getPracticalTaskDtoByTopicId(id);
        model.addAttribute("task", task);
        return "info/practical_task_info";
    }

}
