package com.altimetrik.service;

import java.util.List;

import com.altimetrik.dto.EmployeeDTO;
import com.altimetrik.exception.ResourceNotFoundException;

public interface EmployeeService {

	List<EmployeeDTO> findAll();

	EmployeeDTO findById(Long employeeId) throws ResourceNotFoundException;

	EmployeeDTO save(EmployeeDTO employee);

	EmployeeDTO update(Long id, EmployeeDTO employee) throws ResourceNotFoundException;

	void delete(Long employeeId) throws ResourceNotFoundException;

}
