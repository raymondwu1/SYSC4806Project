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
    private String code;
    private String description;
    private int score;
    private boolean hasUpvoted = false;
    private boolean hasDownvoted = false;
    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private Subscription subscription;
    private java.util.Date expiryDate;

    /* Constructors */
    public Perk(String code, String description, java.util.Date expiryDate) {
        this.code = code;
        this.description = description;
        this.expiryDate = expiryDate;
        this.score = 0;
    }

    public Perk(String code, String description, Subscription sub)
    {
        this.code = code;
        this.description = description;
        this.subscription = sub;
        this.score = 0;
    }

    /* We need at least  a name and description to make a perk, default expiry is never. */

    public Perk(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Perk()
    {
    }

    /* Getters & Setters */

    public String getCode() {
        return code;
    }

    public void setCode(String name) {
        this.code = name;
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
        if ((this.code ==null) ? (p.code !=null) : !this.code.equals(p.code)){
            return false;
        }
        if ((this.description==null) ? (p.description !=null) : !this.description.equals(p.description)) {
            return false;
        }
        if ((this.expiryDate==null) ? (p.expiryDate !=null) : !this.expiryDate.equals(p.expiryDate))
            return false;
        if (this.score!=p.score)
            return false;
        return true;
    }

    public void setSubscription(Subscription subscription){this.subscription=subscription;}

    public int getScore() {
        return score;
    }

    public void upvote() {
        if (!hasDownvoted && !hasUpvoted){
            this.score++;
            hasUpvoted = true;
        }
        else if (hasUpvoted){
            this.score--;
            hasUpvoted = false;
            hasDownvoted = false;
        }
        else{
            this.score++;
            this.score++;
            hasUpvoted = true;
            hasDownvoted = false;
        }
    }

    public void downvote() {
        if (!hasDownvoted && !hasUpvoted){
            this.score--;
            hasDownvoted = true;
        }
        else if (hasDownvoted){
            this.score++;
            hasDownvoted = false;
            hasUpvoted = false;
        }
        else{
            this.score--;
            this.score--;
            hasUpvoted = false;
            hasDownvoted = true;
        }
    }
}
