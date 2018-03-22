package com;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "Perk", path = "Perk")
public interface PerkRepository extends PagingAndSortingRepository<Perk, Long> {
    Perk findByCode(String code);
    boolean existsByCode(String code);
}