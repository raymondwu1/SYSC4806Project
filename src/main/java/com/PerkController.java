package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PerkController {

    @Autowired
    private PerkService perkService;

    private Perk perk;

    @PostMapping(value = "/upvote")
    public void upvote(@ModelAttribute("perk") Perk perk, Model model){
        perkService.save(perk);
    }

    @PostMapping(value = "/downvote")
    public void downvote(@ModelAttribute("perk") Perk perk, Model model){
        perkService.save(perk);
    }
}
