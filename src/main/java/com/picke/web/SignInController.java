package com.picke.web;

import com.picke.dao.UserDAO;
import com.picke.entity.User;
import com.picke.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class SignInController {

    private static final Logger logger = Logger.getLogger(SignInController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView accesssDenied(Principal user) {

        ModelAndView model = new ModelAndView();

        if (user != null) {
            model.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg",
                    "You do not have permission to access this page!");
        }

        model.setViewName("error");

        logger.error(model);

        return model;

    }

    @RequestMapping(value = {"/sign_in", "/"}, method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }

        model.setViewName("sign_in");

        logger.error(model);

        return model;
    }

    @RequestMapping(value =  "/**", method = RequestMethod.GET)
    public ModelAndView errorPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("msg", "Wrong url");
        model.setViewName("404");

        logger.error(model);

        return model;
    }

}