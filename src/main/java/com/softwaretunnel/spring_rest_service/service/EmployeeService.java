package com.softwaretunnel.spring_rest_service.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.softwaretunnel.spring_rest_service.persistance.entity.Employee;
import com.softwaretunnel.spring_rest_service.repository.EmployeeRepository;

@Service
@Scope("prototype")
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

	public Employee insertEmployeeRecord(Employee employee) throws Exception {
		log.info("insertEmployeeRecord method called");
		try {
			return employeeRepository.save(employee);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception ::" + e.getMessage());
			throw e;
		}

	}

	public Employee updateEmployeeRecord(Employee employee) throws Exception {
		log.info("updateEmployeeRecord method called");
		try {
			return employeeRepository.save(employee);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception ::" + e.getMessage());
			throw e;
		}
	}

	public void deleteEmployeeRecord(Long id) throws Exception {
		log.info("deleteEmployeeRecord method called");
		try {
			employeeRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception ::" + e.getMessage());
			throw e;
		}

	}

	public List<Employee> getEmployeeRecords() throws Exception {
		log.info("getEmployeeRecords method called");
		try {
			List<Employee> employeeList = employeeRepository.findAll();
			return employeeList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception ::" + e.getMessage());
			throw e;
		}
	}

}
