package com.softwaretunnel.spring_rest_service.controller;

import java.util.List;

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
@RequestMapping("/crudapi")
public class EmployeeController {

	private final EmployeeService service;

	EmployeeController(EmployeeService service) {
		this.service = service;
	}

	@PostMapping("/create-employee")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee newEmployee) {
		try {
			Employee employee = service.insertEmployeeRecord(newEmployee);
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/get-employees")
	ResponseEntity<List<Employee>> getEmployees() {
		try {
			List<Employee> employees = service.getEmployeeRecords();
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@DeleteMapping("/delete-employee/{id}")
	ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
		try {
			service.deleteEmployeeRecord(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PutMapping("/update-employee/{id}")
	ResponseEntity<Employee> replaceEmployee(@RequestBody Employee employeeToUpdate, @PathVariable Long id) {

		try {
			Employee updatedEmployee = service.updateEmployeeRecord(employeeToUpdate);
			return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
