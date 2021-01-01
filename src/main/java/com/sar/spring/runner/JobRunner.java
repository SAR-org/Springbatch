package com.sar.spring.runner;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class JobRunner {

	private static final Logger logger = LoggerFactory.getLogger(JobRunner.class);
	
	private static String FILE_NAME_KEY = "fileName";
	
	@Autowired
	private JobLauncher simpleJobLauncher;
	
	@Autowired
	private Job demoJob;
	
	@Async
	public void runBatchJob() {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString(FILE_NAME_KEY, "employee.csv");
		jobParametersBuilder.addDate("date", new Date(),true);
		runJob(demoJob,jobParametersBuilder.toJobParameters());
	}
	
	public void runJob(Job job, JobParameters parameters) {
		try {
			JobExecution jobExecution = simpleJobLauncher.run(job, parameters);
		}catch (JobExecutionAlreadyRunningException e) {
			logger.info("Job with filename={} is already running.",parameters.getParameters().get(FILE_NAME_KEY));
		}catch(JobRestartException e) {
			logger.info("Job with filename={} was not restarted.",parameters.getParameters().get(FILE_NAME_KEY));
		}catch(JobInstanceAlreadyCompleteException e) {
			logger.info("Job with filename={} is already completed.",parameters.getParameters().get(FILE_NAME_KEY));
		}catch(JobParametersInvalidException e) {
			logger.info("Invalid job paramters",parameters.getParameters().get(FILE_NAME_KEY));
		}
	}
}
