package com.nfcs.management.ital.model;

import javax.persistence.Entity;

@Entity
public class Activity extends BaseEntity {

	private ActivityType activityType;
	private String description;
	private String comment;
	private Period period;
	private double jobCost;
	private Metric metric;
	private double metricValue;
	
	public ActivityType getActivityType() {
		return activityType;
	}
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Period getPeriod() {
		return period;
	}
	public void setPeriod(Period period) {
		this.period = period;
	}
	public double getJobCost() {
		return jobCost;
	}
	public void setJobCost(double jobCost) {
		this.jobCost = jobCost;
	}
	public Metric getMetric() {
		return metric;
	}
	public void setMetric(Metric metric) {
		this.metric = metric;
	}
	public double getMetricValue() {
		return metricValue;
	}
	public void setMetricValue(double metricValue) {
		this.metricValue = metricValue;
	}
	
	public double getYearTotal() {
		return this.metricValue * this.period.getValue();
	}
	
	public double getMonthTotal() {
		return this.getYearTotal() / 12;
	}
}
