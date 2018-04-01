package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Perk findByCode(String code){
        return perkRepository.findByCode(code);
    }


    public boolean existsByCode(String code){
        return perkRepository.existsByCode(code);
    }

    public Iterable<Perk> findAll(){ return perkRepository.findAll();}
}
