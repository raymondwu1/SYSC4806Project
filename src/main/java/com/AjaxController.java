package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    List<SubPerkPair> subPerkPairList = new ArrayList<>();


    public static final Comparator<SubPerkPair> VOTES_COMPARATOR = new Comparator<SubPerkPair>() {
        // Overriding the compare method to sort the age
        public int compare(SubPerkPair sp, SubPerkPair sp1) {
            return sp1.getPerk().getScore()-sp.getPerk().getScore();
        }
    };

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
                subPerkPairList.add(new SubPerkPair(subs.get(i), subs.get(i).getPerks().get(n)));
            }
        }

        Collections.sort(subPerkPairList, VOTES_COMPARATOR);

        for (SubPerkPair sp: subPerkPairList) {
            ret += "<tr><td class=\"class_sub_name\" id = \"subscription_name\">" + sp.getSubscription().getName() + "</td>" +
                    "<td class=\"class_perk_name\" id = \"perk_name\">" + sp.getPerk().getCode() + "</td>"+
                    "<td class=\"class_desc_name\">" + sp.getPerk().getDescription() + "</td>"+
                    "<td class=\"class_date_name\">" + new SimpleDateFormat("yyyy-MM-dd").format(sp.getPerk().getExpiryDate()) + "</td>" +
                    "<td><button class=\"upvotebutton btn btn-info\">Upvote</button></td>" +
                    "<td><button class=\"downvotebutton btn btn-danger\">Downvote</button></td>" +
                    "<td class=\"class_vote_name\" id = \"score_id\">" + sp.getPerk().getScore() + "</td></tr>";
        }

        subPerkPairList.clear();

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
