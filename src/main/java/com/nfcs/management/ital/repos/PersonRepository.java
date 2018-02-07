package com.nfcs.management.ital.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.nfcs.management.ital.model.Person;
 
@Repository
@RepositoryRestResource
public interface PersonRepository extends BaseEntityRepository<Person, Long> {
 
}
