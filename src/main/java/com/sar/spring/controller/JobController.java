package com.sar.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sar.spring.runner.JobRunner;

@RestController
@RequestMapping("/job")
public class JobController {

	@Autowired
	private JobRunner jobRunner;
	
	@GetMapping("/run")
	public String runJob() {
		jobRunner.runBatchJob();
		return "Job is submitted successfully";
	}
}
