package com.softwaretunnel.spring_rest_service.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.softwaretunnel.spring_rest_service.persistance.entity.Employee;
import com.softwaretunnel.spring_rest_service.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public Employee insertEmployeeRecord(Employee employee) throws Exception {

		try {
			return employeeRepository.save(employee);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public Employee updateEmployeeRecord(Employee employee) throws Exception {

		try {
			return employeeRepository.save(employee);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteEmployeeRecord(Long id) throws Exception {

		try {
			employeeRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public List<Employee> getEmployeeRecords() throws Exception {

		try {
			List<Employee> employeeList = employeeRepository.findAll();
			return employeeList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
