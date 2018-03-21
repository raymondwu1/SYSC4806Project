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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Subscription getSubscription(){return subscription;}

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
