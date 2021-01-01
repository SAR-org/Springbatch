package com.sar.spring.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.sar.spring.dto.EmployeeDTO;
import com.sar.spring.model.Employee;

@Component
public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, Employee>{

	@Override
	public Employee process(EmployeeDTO item) throws Exception {
		Employee employee = new Employee();
		employee.setEmployeeId("NEW "+item.getEmployeeId());
		employee.setFirstName(item.getFirstName());
		employee.setLastName(item.getLastName());
		employee.setEmailId(item.getEmailId());
		employee.setGender(item.getGender());
		System.out.println("Current instance=============>>>>"+employee);
		return employee;
	}

	
}
