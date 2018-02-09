package com.nfcs.management.ital.model;

import javax.persistence.Entity;

@Entity
public class Metric extends BaseEntity {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
