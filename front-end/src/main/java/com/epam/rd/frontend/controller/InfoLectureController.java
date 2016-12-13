package com.epam.rd.frontend.controller;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.frontend.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InfoLectureController {

    private LectureService lectureService;

    @Autowired
    public InfoLectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @RequestMapping(value = "/info/lecture/{id}", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, @PathVariable("id") final Long id) {
        LectureDto lectureDto = lectureService.getLectureById(id);
        model.addAttribute("lecture", lectureDto);
        return "info/lecture_info";
    }

    @RequestMapping(value = "/info/topic/{id}/lecture", method = RequestMethod.GET)
    public String printWelcomeByTopicId(ModelMap model, @PathVariable("id") final Long id) {
        LectureDto lectureDto = lectureService.getLectureDtoByTopicId(id);
        model.addAttribute("lecture", lectureDto);
        return "info/lecture_info";
    }
}