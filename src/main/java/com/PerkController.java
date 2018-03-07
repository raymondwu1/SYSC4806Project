package com;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PerkController {

    private PerkService perkService;

    @RequestMapping(value = {"/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        if (!model.containsAttribute("perkForm")) {
            model.addAttribute("perkForm", new Perk());
        }
        return "welcome";
    }

    @RequestMapping(value = {"/welcome"}, method = RequestMethod.POST, params="method=AddPerk")
    public String addPerk(@ModelAttribute("perkForm") Perk perkForm, BindingResult bindingResult, Model model) {

        if (!perkService.existsByName(perkForm.getName())){
            perkService.save(perkForm);
        }

        return "welcome";
    }

    @RequestMapping(value = {"/welcome"}, method = RequestMethod.POST, params="method=RemovePerk")
    public String removePerk(@ModelAttribute("perkForm") Perk perkForm, BindingResult bindingResult, Model model) {

        if (perkService.existsByName(perkForm.getName())){
            perkService.delete(perkForm);
        }

        return "welcome";
    }
}
