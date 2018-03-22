package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PerkController {

    @Autowired
    private PerkService perkService;

    private Perk perk;

    @PostMapping(value = "/upvote",consumes = {"application/json"})
    public void upvote(@RequestBody Perk perkJson){
        perk = perkService.findByName(perkJson.getName());
        perk.upvote();
        perkService.save(perk);
    }

    @PostMapping(value = "/downvote",consumes = {"application/json"})
    public void downvote(@RequestBody Perk perkJson){
        perk = perkService.findByName(perkJson.getName());
        perk.downvote();
        perkService.save(perk);
    }
}
