package com.nfcs.management.ital.controller.rest;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.nfcs.management.ital.model.ActivityType;
import com.nfcs.management.ital.repos.ActivityTypeRepository;

@Controller
@RequestMapping("/activitytypes")
public class ActivityTypeController extends BaseController<ActivityType> {
	
	@Autowired
	ActivityTypeRepository per;

	public ActivityTypeController() {
		ber = per;
	}
	
	@PostConstruct
	public void postConstruct() {	
		ber = per;
	}
}
