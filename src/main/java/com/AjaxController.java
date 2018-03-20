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

    @RequestMapping(method=RequestMethod.GET, value = "/GetTable")
    public String GetTable(@RequestParam String userName)
    {
        String ret = "";
        user = userService.findByUsername(userName);
        List<Subscription> subs = user.getSubscriptions();

        for(int i = 0; i < subs.size(); i++) {
            for(int n = 0; n < subs.get(i).getPerks().size(); n++) {
                ret += "<tr><td>" + subs.get(i).getName() + "</td><td>" + subs.get(i).getPerks().get(n).getName() + "</td>";
            }
        }

        /*Iterable<Perk> perks = perkService.findAll();
        Iterator<Perk> it = perks.iterator();
        while(it.hasNext())
        {
            Perk p = it.next();
            System.out.print("Perk"+p.getName());
            for(int i = 0; i < subs.size(); i++) {
                System.out.print("SUBS" + subs.get(i).getName());
                if (p.getSubscription().equals(subs.get(i))) {
                    ret += "<tr><td>" + p.getSubscription().getName() + "</td><td>" + p.getName() + "</td>";
                }
            }
        }*/
        System.out.println("FINAL"+ret);
        return ret;
    }

    @RequestMapping(method=RequestMethod.POST, value = "/AddSubscription",consumes = {"application/json"})
    public ResponseEntity<Subscription> AddSubscription(@RequestParam String userName,@RequestBody Subscription subscriptionJson)
    {
        System.out.println("SUB "+subscriptionJson.getName());
        boolean save = true;
        user = userService.findByUsername(userName);
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
        return new ResponseEntity<Subscription>(HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST, value = "/AddPerk",consumes = {"application/json"})
    public void AddPerk(@RequestParam String userName,@RequestParam String subName,@RequestBody Perk perkJson)
    {
        System.out.println("PERK "+perkJson.getName());
        boolean save = true;
        int index = -1;
        user = userService.findByUsername(userName);

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
