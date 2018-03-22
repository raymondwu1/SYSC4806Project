package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxController {
    @Autowired
    private UserService userService;
    @Autowired
    private PerkService perkService;
    @Autowired
    private SubscriptionService subscriptionService;

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
                ret += "<tr><td>" + subs.get(i).getName() + "</td><td>" + subs.get(i).getPerks().get(n).getCode() + "</td><td>" + subs.get(i).getPerks().get(n).getDescription() + "</td><td>" + new SimpleDateFormat("yyyy-MM-dd").format(subs.get(i).getPerks().get(n).getExpiryDate()) + "</td>";
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
        user = userService.findByUsername(userName);

        if (!subscriptionService.existsByName(subscriptionJson.getName())) {
            subscriptionService.save(subscriptionJson);
        }

        Subscription newSub = subscriptionService.findByName(subscriptionJson.getName());
        if (!user.getSubscriptions().contains(newSub)) {
            user.addSubscription(newSub);
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
    public void AddPerk(@RequestParam String userName,@RequestParam String subName,@RequestBody Perk perkJson) {
        user = userService.findByUsername(userName);

        if (subscriptionService.existsByName(subName)) {
            Subscription sub = subscriptionService.findByName(subName);
            if (!sub.getPerks().contains(perkJson)) {
                sub.addPerk(perkJson);
                subscriptionService.save(sub);
            }
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
