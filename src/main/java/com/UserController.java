package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    private User user;

    @RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model){
        return "home";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        if (!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", new User());
        }

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String register(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        else if (userService.findByUsername(userForm.getUsername()) != null) {
            return "registration";
        }

        else if (!userForm.getConfirmPassword().equals(userForm.getPassword())) {
            return "registration";
        }

        userService.save(userForm);
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {

        if (!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", new User());
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginEnter(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (!userService.existsByUsername(userForm.getUsername())) {
            return "login";
        }

        else if (bindingResult.hasErrors()) {
            return "login";
        }

        user = userService.findByUsername(userForm.getUsername());
        return "welcome";
    }

    @RequestMapping(value = {"/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        if (!model.containsAttribute("subscriptionForm")) {
            model.addAttribute("subscriptionForm", new Subscription());
        }
        return "welcome";
    }

    @RequestMapping(value = {"/welcome"}, method = RequestMethod.POST, params="method=AddSubscription")
    public String addSubscription(@ModelAttribute("subscriptionForm") Subscription subscriptionForm, BindingResult bindingResult, Model model) {

        if (!user.getSubscriptions().contains(subscriptionForm)) {
            user.addSubscription(subscriptionForm);
        }

        return "welcome";
    }

    @RequestMapping(value = {"/welcome"}, method = RequestMethod.POST, params="method=RemoveSubscription")
    public String removeSubscription(@ModelAttribute("subscriptionForm") Subscription subscriptionForm, BindingResult bindingResult, Model model) {

        if (user.getSubscriptions().contains(subscriptionForm)) {
            user.removeSubscription(subscriptionForm);
        }

        return "welcome";
    }

}