package com.sbt.management.ital.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.sbt.management.ital.model.Person;
 
@Repository
@RepositoryRestResource
public interface PersonRepository extends JpaRepository<Person, Long> {
 
}
