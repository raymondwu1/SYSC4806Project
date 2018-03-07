package com;

import org.springframework.beans.factory.annotation.Autowired;
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
    @ResponseBody
    public String GetTable(@RequestParam String userName)
    {
        String ret = "";
        user = userService.findByUsername(userName);
        List<Subscription> subs = user.getSubscriptions();

        for(int i = 0; i < subs.size(); i++) {
            System.out.println("PERKS" + subs.get(i).getPerks().size());
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

    @RequestMapping(method=RequestMethod.GET, value = "/AddSubscription")
    public void AddSubscription(@RequestParam String userName,@RequestParam String subName)
    {
        boolean save = true;
        user = userService.findByUsername(userName);
        for(int i=0; i < user.getSubscriptions().size();i++)
        {
            if (user.getSubscriptions().get(i).getName().equals(subName)) {
                save = false;
            }
        }
        if(save) {
            user.addSubscription(new Subscription(subName));
            userService.save(user);
        }
    }

    @RequestMapping(method=RequestMethod.GET, value = "/AddPerk")
    public void AddPerk(@RequestParam String userName,@RequestParam String perkName,@RequestParam String subName,@RequestParam String desc)
    {
        boolean save = true;
        int index = -1;
        user = userService.findByUsername(userName);

        List<Subscription> subs = user.getSubscriptions();
        for(int i=0; i < subs.size();i++)
        {
            System.out.println(subName + " 0" +subs.get(i).getName());
            if(subName.equals(subs.get(i).getName())) {
                for(int n=0; n < subs.get(i).getPerks().size();n++)
                {
                    if(subs.get(i).getPerks().get(n).getName().equals(perkName)) {
                        save = false;
                    }
                }
                index = i;
            }
        }
        if (save && index != -1) {

            Perk p = new Perk(perkName,desc,subs.get(index));
            user.getSubscriptions().get(index).getPerks().add(p);
            userService.save(user);
        }
    }
}
