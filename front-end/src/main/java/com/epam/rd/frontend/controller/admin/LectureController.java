package com.epam.rd.frontend.controller.admin;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.LectureDtoCreate;
import com.epam.rd.frontend.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/admin/lecture")
public class LectureController {
    private LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @RequestMapping(value = "/add", method = GET)
    public ModelAndView addLecture(@RequestParam("topicId") Long topicId) {
        ModelAndView modelAndView = new ModelAndView("admin/new_lecture");
        LectureDtoCreate dtoCreate = new LectureDtoCreate();
        dtoCreate.setTopicId(topicId);
        modelAndView.addObject("lecture", dtoCreate);
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = POST)
    public String createLecture(@ModelAttribute("lecture") LectureDtoCreate dtoCreate) {
        lectureService.addLecture(dtoCreate);
        return "redirect:/rd/admin/topic/" + dtoCreate.getTopicId();
    }

    @RequestMapping(value = "/edit", method = GET)
    public ModelAndView editLecture(@RequestParam("topicId") Long topicId) {
        ModelAndView modelAndView = new ModelAndView("admin/edit_lecture");
        LectureDto lectureDto = lectureService.getLectureDtoByTopicId(topicId);
        modelAndView.addObject("lecture", lectureDto);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = POST)
    public String updateLecture(@ModelAttribute("lecture") LectureDto dto) {
        LectureDto lectureDto = lectureService.updateLecture(dto);
        return "redirect:/rd/admin/topic/" + lectureDto.getTopicId();
    }
}
