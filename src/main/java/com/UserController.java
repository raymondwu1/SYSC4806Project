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
            return "registrationErrorPage";
        }

        else if (userService.findByUsername(userForm.getUsername()) != null) {
            return "registrationErrorPage";
        }

        else if (!userForm.getConfirmPassword().equals(userForm.getPassword())) {
            return "registrationErrorPage";
        }

        userService.save(userForm);
        return "welcome";
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
            return "CredentialsErrorPage";
        }
        else if (bindingResult.hasErrors()) {
            return "CredentialsErrorPage";
        }

        user = userService.findByUsername(userForm.getUsername());
        if (user.getPassword().equals(userForm.getPassword())) {
            return "welcome";
        }
        return "CredentialsErrorPage";
    }

    @RequestMapping(value = {"/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        if (!model.containsAttribute("subscriptionForm")) {
            model.addAttribute("subscriptionForm", new Subscription());
        }
        return "welcome";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String forgotPassword(Model model){
        if (!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", new User());
        }
        return "forgotPassword";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public String forgotPassword(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        user = userService.findByUsername(userForm.getUsername());

        if (bindingResult.hasErrors()) {
            return "forgotPasswordErrorPage";
        }

        else if (user == null) {
            return "forgotPasswordErrorPage";
        }

        else if (!userForm.getConfirmPassword().equals(userForm.getPassword())) {
            return "forgotPasswordErrorPage";
        }

        else if (user.getPassword().equals(userForm.getPassword())) {
            return "forgotPasswordErrorPage";
        }

        userService.save(userForm);
        return "welcome";
    }


}