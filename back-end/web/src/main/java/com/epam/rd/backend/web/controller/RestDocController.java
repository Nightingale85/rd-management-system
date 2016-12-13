package com.epam.rd.backend.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class RestDocController {
    @RequestMapping(value = "/rest", method = GET)
    public ModelAndView getJsonDoc(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("jsondoc-ui");
        StringBuffer requestURL = request.getRequestURL();
        String jsondocPath = requestURL.toString().replaceAll("rest", "jsondoc");
        modelAndView.addObject("jsondocPath", jsondocPath);
        return modelAndView;
    }
}
