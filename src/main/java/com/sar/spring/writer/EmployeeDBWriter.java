package com.sar.spring.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sar.spring.model.Employee;
import com.sar.spring.repository.EmployeeRepository;

@Component
public class EmployeeDBWriter implements ItemWriter<Employee>{
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeDBWriter.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		employeeRepository.saveAll(employees);
		logger.info("{} employees saved in database",employees.size());
	}

}
