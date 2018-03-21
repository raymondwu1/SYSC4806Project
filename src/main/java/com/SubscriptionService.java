package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;

    public void save(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public void delete (Subscription subscription){
        subscriptionRepository.delete(subscription);
    }

    public Subscription findByName(String name){
        return subscriptionRepository.findByName(name);
    }

    public boolean existsByName(String name){
        return subscriptionRepository.existsByName(name);
    }

    public Iterable<Subscription> findAll(){return subscriptionRepository.findAll();}

}
