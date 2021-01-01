package com.sar.spring.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.sar.spring.dto.EmployeeDTO;

public class EmployeeFileRowMapper implements FieldSetMapper<EmployeeDTO>{

	@Override
	public EmployeeDTO mapFieldSet(FieldSet fieldSet) throws BindException {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeId(fieldSet.readString("employeeId"));
		employeeDTO.setFirstName(fieldSet.readString("firstName"));
		employeeDTO.setLastName(fieldSet.readString("lastName"));
		employeeDTO.setEmailId(fieldSet.readString("email"));
		employeeDTO.setGender(fieldSet.readString("gender"));
		
		return employeeDTO;
	}
	
	

}
