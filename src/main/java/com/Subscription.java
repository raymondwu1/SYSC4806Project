/*
* This class holds the actual subscription of users
* that holds available perks
*
* */
package com;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subscription {
    /* Subscription fields */
    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private List<Perk> perks;
    @Id
    @Column(unique=true)
    private String name;
    private String fee;
    private static final String FREE = "FREE";

    /* Constructors */
    public Subscription(String name, String fee)
    {
        this.name = name;
        this.fee = fee;
        this.perks = new ArrayList<Perk>();
    }

    /* We need atleast  name to make a membership, default membership fee is free. */
    public Subscription(String name)
    {
        this(name, FREE);
    }

    public Subscription()
    {

    }

    /* Add/Remove Perk */
    public void addPerk(Perk p)
    {
        this.perks.add(p);
    }


    public void removePerk(Perk p)
    {
        this.perks.remove(p);
    }

    /* Getters & Setters */

    public List<Perk> getPerks() {
        return perks;
    }

    public void setPerks(ArrayList<Perk> perks) {
        this.perks = perks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if(!(o instanceof Subscription)) {
            return false;
        }
        Subscription s = (Subscription) o;
        if (this.name != s.name){
            return false;
        }
        if (this.fee != s.fee) {
            return false;
        }
        if (!this.perks.containsAll(s.getPerks()))
            return false;
        return true;
    }


}
