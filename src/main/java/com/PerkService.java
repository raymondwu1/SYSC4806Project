package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerkService {
    @Autowired
    private PerkRepository perkRepository;

    public void save(Perk perk){
        perkRepository.save(perk);
    }

    public void delete(Perk perk){
        perkRepository.delete(perk);
    }

    public Perk findByName(String name){
        return perkRepository.findByName(name);
    }

    public boolean existsByName(String name){
        return perkRepository.existsByName(name);
    }

}
