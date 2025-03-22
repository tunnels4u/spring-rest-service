package com.softwaretunnel.spring_rest_service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softwaretunnel.spring_rest_service.persistance.entity.Employee;
import com.softwaretunnel.spring_rest_service.service.EmployeeService;

@RestController
@Scope("prototype")
@RequestMapping("/crudapi")
public class EmployeeController {

	private final EmployeeService service;
	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	EmployeeController(EmployeeService service) {
		this.service = service;
	}

	@PostMapping("/create-employee")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee newEmployee) {
		log.info("createEmployee method called");
		try {
			Employee employee = service.insertEmployeeRecord(newEmployee);
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception ::" + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/get-employees")
	ResponseEntity<List<Employee>> getEmployees() {
		log.info("getEmployees method called");
		try {
			List<Employee> employees = service.getEmployeeRecords();
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception ::" + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@DeleteMapping("/delete-employee/{id}")
	ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
		log.info("deleteEmployee method called");
		try {
			service.deleteEmployeeRecord(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception ::" + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PutMapping("/update-employee")
	ResponseEntity<Employee> replaceEmployee(@RequestBody Employee employeeToUpdate) {
		log.info("replaceEmployee method called");
		try {
			Employee updatedEmployee = service.updateEmployeeRecord(employeeToUpdate);
			return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception ::" + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
