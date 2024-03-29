package com.altimetrik.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altimetrik.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

	
}
