package com.picke.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView welcomePage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring MVC");
        model.addObject("message", "This is welcome page!");
        model.setViewName("home_page");
        return model;

    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ModelAndView infoPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("name", "admin");
        model.addObject("surname", "admin");
        model.setViewName("info_page");
        return model;

    }

}