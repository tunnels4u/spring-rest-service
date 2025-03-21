package com.softwaretunnel.spring_rest_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.softwaretunnel.spring_rest_service.persistance.entity.Employee;
import com.softwaretunnel.spring_rest_service.service.EmployeeService;

@SpringBootTest
public class ServiceTest {

	@Autowired
	EmployeeService employeeService;

	@Test
	void contextLoads() throws Exception {
		assertThat(employeeService).isNotNull();
	}

	@Test
	public void testInsertEmployeeRecords() {

		String firstName = "Tom";
		String lastName = "Greg";
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);

		try {
			employeeService.insertEmployeeRecord(employee);
			List<Employee> employees = employeeService.getEmployeeRecords();
			assertTrue(employees.size() == 1);
			Employee employeeInserted = employees.get(0);
			assertTrue(employeeInserted.getFirstName().equals(employee.getFirstName()));
			assertTrue(employeeInserted.getLastName().equals(employee.getLastName()));
			assertTrue(employeeInserted.getId() != null && employeeInserted.getId() != 0);

			// flush
			employeeService.deleteEmployeeRecords(employeeInserted);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testUpdateEmployeeRecords() {

		String firstName = "Tom";
		String lastName = "Greg";
		String updatedLastName = "Gregman";
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		try {
			employeeService.insertEmployeeRecord(employee);
			List<Employee> employees = employeeService.getEmployeeRecords();
			Employee employeeInserted = employees.get(0);
			employeeInserted.setLastName(updatedLastName);

			// update & check
			employeeService.updateEmployeeRecords(employeeInserted);
			List<Employee> updatedEmployees = employeeService.getEmployeeRecords();
			assertTrue(updatedEmployees.size() == 1);
			Employee updatedEmployee = employees.get(0);
			assertTrue(updatedEmployee.getLastName().equals(updatedLastName));

			// flush
			employeeService.deleteEmployeeRecords(employeeInserted);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testDeleteEmployeeRecords() {

		String firstName = "Tom";
		String lastName = "Greg";
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		try {
			employeeService.insertEmployeeRecord(employee);
			List<Employee> employees = employeeService.getEmployeeRecords();
			Employee employeeInserted = employees.get(0);

			// delete & check
			employeeService.deleteEmployeeRecords(employeeInserted);
			List<Employee> insertedEmployees = employeeService.getEmployeeRecords();
			assertTrue(insertedEmployees.size() == 0);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testGetEmployeeRecords() {

		// employee 1
		Employee employee1 = new Employee();
		employee1.setFirstName("Tom");
		employee1.setLastName("Greg");

		// employee 2
		Employee employee2 = new Employee();
		employee2.setFirstName("Sandra");
		employee2.setLastName("David");
		try {
			employeeService.insertEmployeeRecord(employee1);
			employeeService.insertEmployeeRecord(employee2);
			List<Employee> employees = employeeService.getEmployeeRecords();
			assertTrue(employees.size() == 2);

			// flush
			employeeService.deleteEmployeeRecords(employees.get(0));
			employeeService.deleteEmployeeRecords(employees.get(1));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
