package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class AjaxController {
    @Autowired
    private UserService userService;
    @Autowired
    private PerkService perkService;

    User user;

    /*
     * Get table rows for a user populated with that users subscription and perk names.
     * @Param: userName=userName of user
     * @Ret:HTML rows of that users subscription and perks
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method=RequestMethod.GET, value = "/GetTable")
    public String GetTable(@RequestParam String userName)
    {
        String ret = "";
        user = userService.findByUsername(userName);//Find user
        List<Subscription> subs = user.getSubscriptions();

        /* For all subscriptions get all perks and append table row. */
        for(int i = 0; i < subs.size(); i++) {
            for(int n = 0; n < subs.get(i).getPerks().size(); n++) {
                ret += "<tr><td>" + subs.get(i).getName() + "</td><td>" + subs.get(i).getPerks().get(n).getName() + "</td>";
            }
        }

        return ret;
    }


    /*
     * Add subscription to user.
     * @Param: userName=userName of user
     * @Param: perkJson=Json of the subscription to be added
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method=RequestMethod.POST, value = "/AddSubscription",consumes = {"application/json"})
    public void AddSubscription(@RequestParam String userName,@RequestBody Subscription subscriptionJson)
    {
        boolean save = true;
        user = userService.findByUsername(userName);
        /* Save the subscription but first check if te subscription is already there. */
        for(int i=0; i < user.getSubscriptions().size();i++)
        {
            if (user.getSubscriptions().get(i).getName().equals(subscriptionJson.getName())) {
                save = false;
            }
        }
        if(save) {
            user.addSubscription(subscriptionJson);
            userService.save(user);
        }
    }


    /*
     * Add perk to a users subscription.
     * @Param: userName=userName of user
     * @Param: subName=name of subscription
     * @Param: perkJson=Json of the perk to be added
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method=RequestMethod.POST, value = "/AddPerk",consumes = {"application/json"})
    public void AddPerk(@RequestParam String userName,@RequestParam String subName,@RequestBody Perk perkJson)
    {
        boolean save = true;
        int index = -1;
        user = userService.findByUsername(userName);
        /* Find subscription of that name and save the perk to that subscription. If the perk already exists don't save.*/
        List<Subscription> subs = user.getSubscriptions();
        for(int i=0; i < subs.size();i++)
        {
            if(subName.equals(subs.get(i).getName())) {
                for(int n=0; n < subs.get(i).getPerks().size();n++)
                {
                    if(subs.get(i).getPerks().get(n).getName().equals(perkJson.getName())) {
                        save = false;
                    }
                }
                index = i;
            }
        }
        if (save && index != -1) {
            user.getSubscriptions().get(index).getPerks().add(perkJson);
            userService.save(user);
        }
    }

    /*
    * Return a list of subscription names for a user
    * @Param: userName = name of user
    * @Ret: List of subscription names
    * */
    @RequestMapping(method=RequestMethod.GET, value = "/GetSubs")
    public List<String> GetSubs(@RequestParam String userName)
    {
        ArrayList<String> subs = new ArrayList<String>();
        user = userService.findByUsername(userName);
        for (Subscription subscription : user.getSubscriptions()) {
            subs.add(subscription.getName());
        }
        return subs;
    }

}
