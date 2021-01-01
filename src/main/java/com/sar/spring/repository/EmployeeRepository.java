package com.sar.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sar.spring.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{

}
