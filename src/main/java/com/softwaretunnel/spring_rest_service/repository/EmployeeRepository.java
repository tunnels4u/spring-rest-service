package com.softwaretunnel.spring_rest_service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.softwaretunnel.spring_rest_service.persistance.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
	Employee save(Employee employee);

	List<Employee> findAll();
	
	void delete(Employee employee);

}
