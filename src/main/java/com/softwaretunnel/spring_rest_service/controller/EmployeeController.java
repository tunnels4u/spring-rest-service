package com.softwaretunnel.spring_rest_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	// end::get-aggregate-root[]

//	@PostMapping("/employees")
//	Employee newEmployee(@RequestBody Employee newEmployee) {
//		return repository.save(newEmployee);
//	}
//
//	  // Single item
//	  
//	  @GetMapping("/employees/{id}")
//	  Employee one(@PathVariable Long id) {
//	    
//	    return repository.findById(id)
//	      .orElseThrow(() -> new EmployeeNotFoundException(id));
//	  }
//
//	  @PutMapping("/employees/{id}")
//	  Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
//	    
//	    return repository.findById(id)
//	      .map(employee -> {
//	        employee.setName(newEmployee.getName());
//	        employee.setRole(newEmployee.getRole());
//	        return repository.save(employee);
//	      })
//	      .orElseGet(() -> {
//	        return repository.save(newEmployee);
//	      });
//	  }
//
//	  @DeleteMapping("/employees/{id}")
//	  void deleteEmployee(@PathVariable Long id) {
//	    repository.deleteById(id);
//	  }
//	}

}
