package com.sagarandcompany.RabbitMQPubSubExample.controller;

import com.sagarandcompany.RabbitMQPubSubExample.model.User;
import com.sagarandcompany.RabbitMQPubSubExample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.publish(user);
            return "redirect:/list";
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("users", userService.getList());
        return modelAndView;
    }
}
