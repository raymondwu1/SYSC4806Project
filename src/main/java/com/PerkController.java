package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class PerkController {
    @Autowired
    private UserService userService;
    @Autowired
    private PerkService perkService;
    @Autowired
    private SubscriptionService subscriptionService;

    User user;

    private Perk perk;

    @PostMapping(value = "/upvote",consumes = {"application/json"})
    public void upvote(@RequestParam String userName,@RequestParam String subName,@RequestBody Perk perkJson) {
        user = userService.findByUsername(userName);

        Perk tempPerk = perkService.findByName(perkJson.getName());

        if (subscriptionService.existsByName(subName)) {
            Subscription sub = subscriptionService.findByName(subName);
            if (sub.getPerks().contains(tempPerk)) {
                tempPerk.upvote();
                perkService.save(tempPerk);
                subscriptionService.save(sub);
            }
        }
    }

    @PostMapping(value = "/downvote",consumes = {"application/json"})
    public void downvote(@RequestParam String userName,@RequestParam String subName,@RequestBody Perk perkJson) {
        user = userService.findByUsername(userName);

        Perk tempPerk = perkService.findByName(perkJson.getName());

        if (subscriptionService.existsByName(subName)) {
            Subscription sub = subscriptionService.findByName(subName);
            if (sub.getPerks().contains(tempPerk)) {
                tempPerk.downvote();
                perkService.save(tempPerk);
                subscriptionService.save(sub);
            }
        }
    }


}
