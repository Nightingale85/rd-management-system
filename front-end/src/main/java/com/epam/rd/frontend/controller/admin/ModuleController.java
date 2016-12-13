package com.epam.rd.frontend.controller.admin;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;
import com.epam.rd.frontend.service.ModuleService;
import com.epam.rd.frontend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class ModuleController {
    private ModuleService moduleService;
    private TopicService topicService;

    @Autowired
    public ModuleController(ModuleService moduleService, TopicService topicService) {
        this.moduleService = moduleService;
        this.topicService = topicService;
    }

    @RequestMapping(value = "/module/{moduleId}", method = RequestMethod.GET)
    public String moduleById(@PathVariable Long moduleId, Model model) {
        ModuleDto moduleDto = moduleService.getModuleById(moduleId);
        List<TopicDto> topicDtos = topicService.getTopicsByModuleId(moduleDto.getId());
        model.addAttribute("topicDtoCreate", new TopicDtoCreate());
        model.addAttribute("topicDtos", topicDtos);
        model.addAttribute("moduleDto", moduleDto);
        return "admin/edit_module";
    }

    @RequestMapping(value = "/module/edit", method = RequestMethod.POST)
    public String updateModule(@ModelAttribute(value = "moduleDto") ModuleDto moduleDto) {
        moduleService.updateModule(moduleDto);
        return "redirect:/rd/admin/module/" + moduleDto.getId();
    }

    @RequestMapping(value = "/topic/add", method = RequestMethod.POST)
    public String createTopic(
            @ModelAttribute(value = "moduleDto") ModuleDto moduleDto,
            @ModelAttribute(value = "topicDtoCreate") TopicDtoCreate topicDtoCreate) {
        topicDtoCreate.setModuleId(moduleDto.getId());
        topicService.addTopic(topicDtoCreate);
        return "redirect:/rd/admin/module/" + moduleDto.getId();
    }
}

