package com.nfcs.management.ital.repos;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.nfcs.management.ital.model.BaseEntity;

//@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {
	
}
