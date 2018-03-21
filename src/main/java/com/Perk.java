/*
* An individual perk held by a subscription, a ubscription can have many perks
*
* */

package com;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Perk {
    /* Perk fields */
    @Id
    @Column(unique=true)
    private String name;
    private String description;
    private int score;
    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private Subscription subscription;
    private java.util.Date expiryDate;

    /* Constructors */
    public Perk(String name, String description, java.util.Date expiryDate)
    {
        this.name = name;
        this.description = description;
        this.expiryDate = expiryDate;
        this.score = 0;
    }

    public Perk(String name, String description, Subscription sub)
    {
        this.name = name;
        this.description = description;
        this.subscription = sub;
        this.score = 0;
    }

    /* We need at least  a name and description to make a perk, default expiry is never. */
    public Perk(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public Perk()
    {
    }

    /* Getters & Setters */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if(!(o instanceof Perk)) {
            return false;
        }
        Perk p = (Perk) o;
        if ((this.name==null) ? (p.name !=null) : !this.name.equals(p.name)){
            return false;
        }
        if ((this.description==null) ? (p.description !=null) : !this.description.equals(p.description)) {
            return false;
        }
        if ((this.expiryDate==null) ? (p.expiryDate !=null) : !this.expiryDate.equals(p.expiryDate))
            return false;
        return true;
    }

    public void setSubscription(Subscription subscription){this.subscription=subscription;}

    public int getScore() {
        return score;
    }

    public void upvote() {
        this.score++;
    }

    public void downvote() {
        this.score--;
    }
}
