package com.picke.web;

import com.picke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value =  "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value =  "/home", method = RequestMethod.GET)
    public ModelAndView welcomePage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring MVC");
        model.addObject("message", "This is welcome page!");
        model.addObject("username", userService.getCurrentUserName());
        model.addObject("authority", userService.getCurrentAuthority());
        model.setViewName("home");
        return model;

    }

    @RequestMapping(value =  "/info", method = RequestMethod.GET)
    public ModelAndView infoPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("name", "admin");
        model.addObject("surname", "admin");
        model.setViewName("info");
        return model;

    }

}