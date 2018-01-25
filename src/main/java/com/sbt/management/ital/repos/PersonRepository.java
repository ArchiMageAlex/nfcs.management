package com.sbt.management.ital.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbt.management.ital.model.Person;
 
@RepositoryRestResource
public interface PersonRepository extends CrudRepository<Person, Long> {
 
}
