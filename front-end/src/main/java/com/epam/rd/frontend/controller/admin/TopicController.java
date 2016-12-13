package com.epam.rd.frontend.controller.admin;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.frontend.service.LectureService;
import com.epam.rd.frontend.service.PracticalTaskService;
import com.epam.rd.frontend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/")
public class TopicController {
    private TopicService topicService;
    private LectureService lectureService;
    private PracticalTaskService practicalTaskService;


    @Autowired
    public TopicController(TopicService topicService, LectureService lectureService,
                           PracticalTaskService practicalTaskService) {
        this.topicService = topicService;
        this.lectureService = lectureService;
        this.practicalTaskService = practicalTaskService;
    }

    @RequestMapping(value = "topic/{id}", method = RequestMethod.GET)
    public String topicEditPage(Model model, @PathVariable("id") final Long topicId) {
        TopicDto topic = topicService.getTopicById(topicId);
        if (topic.getLectureId() != null) {
            LectureDto lecture = lectureService.getLectureDtoByTopicId(topicId);
            model.addAttribute("lecture", lecture);
        }
        if (topic.getPracticalTaskId() != null) {
            PracticalTaskDto task = practicalTaskService.getPracticalTaskDtoByTopicId(topicId);
            model.addAttribute("task", task);
        }
        model.addAttribute("topic", topic);
        return "admin/edit_topic";
    }

    @RequestMapping(value = "topic/edit", method = RequestMethod.POST)
    public String updateTopic(@ModelAttribute(value = "topic") TopicDto dto) {
        return "redirect:/rd/admin/module/" + topicService.updateTopic(dto).getModuleId();
    }

}
