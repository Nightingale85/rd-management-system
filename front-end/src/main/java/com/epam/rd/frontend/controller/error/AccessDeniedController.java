package com.epam.rd.frontend.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class AccessDeniedController {

    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public ModelAndView accessDenied(Principal user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/access_denied");
        modelAndView.addObject("userName", user.getName());
        return modelAndView;
    }
}

