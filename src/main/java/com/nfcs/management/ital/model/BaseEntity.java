package com.nfcs.management.ital.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	public Long getId() {
		return id;
	}

}