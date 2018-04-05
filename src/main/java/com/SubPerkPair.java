package com;

public class SubPerkPair {

    private Perk perk;
    private Subscription subscription;

    public SubPerkPair(Subscription subscription, Perk perk) {
        this.perk = perk;
        this.subscription = subscription;
    }

    public Perk getPerk() {
        return perk;
    }

    public void setPerk(Perk perk) {
        this.perk = perk;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}
