package com.altimetrik.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altimetrik.dto.EmployeeDTO;
import com.altimetrik.exception.ResourceNotFoundException;
import com.altimetrik.model.Employee;
import com.altimetrik.repository.EmployeeRepository;
import com.altimetrik.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<EmployeeDTO> findAll() {
		EmployeeHolder employeeHolder = new EmployeeHolder();
		employeeRepository.findAll().forEach(emp -> employeeHolder.addRecord(emp));
		return employeeHolder.getEmployees();
	}

	@Override
	public EmployeeDTO findById(Long employeeId) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findOne(employeeId);
		if (employee == null) {
			throw new ResourceNotFoundException("Employee not found for this id :: " + employeeId);
		}
		EmployeeDTO employeeDTO = new EmployeeDTO();
		BeanUtils.copyProperties(employee, employeeDTO);
		return employeeDTO;
	}

	@Override
	public EmployeeDTO save(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);
		employee.setId(null);
		employee = employeeRepository.save(employee);
		BeanUtils.copyProperties(employee, employeeDTO);
		return employeeDTO;
	}

	@Override
	public EmployeeDTO update(Long id, EmployeeDTO employeeDTO) throws ResourceNotFoundException {

		try {
			Employee employee = employeeRepository.findOne(id);
			if (employee == null) {
				throw new ResourceNotFoundException("Resource not found with id " + id);
			}
			BeanUtils.copyProperties(employeeDTO, employee);
			employee = employeeRepository.save(employee);
			BeanUtils.copyProperties(employee, employeeDTO);
			return employeeDTO;
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Override
	public void delete(Long employeeId) throws ResourceNotFoundException {
		try {
			Employee employee = employeeRepository.findOne(employeeId);
			if (employee == null) {
				throw new ResourceNotFoundException("Resource not found with id " + employeeId);
			}
			employeeRepository.delete(employeeRepository.findOne(employeeId));
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

}

class EmployeeHolder {
	private List<EmployeeDTO> employees = null;

	public EmployeeHolder() {
		employees = new ArrayList<>();
	}

	public void addRecord(Employee employee) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		BeanUtils.copyProperties(employee, employeeDTO);
		employees.add(employeeDTO);
	}

	public List<EmployeeDTO> getEmployees() {
		return employees;
	}
}