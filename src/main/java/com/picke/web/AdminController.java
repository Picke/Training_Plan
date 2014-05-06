package com.picke.web;

import com.google.api.services.calendar.Calendar;
import com.picke.service.GoogleCalendarUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {


    public static final Logger logger = Logger.getLogger(AdminController.class.getName());

    private Calendar service = null;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView welcomePage() {
        if (service == null) {
            try {
                service = GoogleCalendarUtil.getCalendarService(GoogleCalendarUtil.CODE);
            } catch (Exception e) {
                logger.warning("Cannot create Calendar service");
            }
        }

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring MVC");
        model.addObject("message", "This is admin's welcome page!");
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

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ModelAndView eventsPage() throws IOException {

        ModelAndView model = new ModelAndView();
        model.addObject("eventList", service.calendarList().list().execute().getItems());
        model.setViewName("events_page");
        return model;

    }

}