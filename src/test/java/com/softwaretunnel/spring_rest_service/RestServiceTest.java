package com.softwaretunnel.spring_rest_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaretunnel.spring_rest_service.persistance.entity.Employee;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestServiceTest {

	@Value("${spring.web.rest.url}")
	private String restUrl;

	@Test
	void contextLoads() throws Exception {
		assertThat(restUrl).isNotNull();
	}

	CloseableHttpClient client = HttpClients.createDefault();

	@Test
	public void testCreateEmployee() {

		String firstName = "Tom";
		String lastName = "Greg";
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);

		try {

			Employee employeeInResponse = callCreateEmployee(employee);

			assertTrue(employeeInResponse.getFirstName().equals(employee.getFirstName()));
			assertTrue(employeeInResponse.getLastName().equals(employee.getLastName()));
			assertTrue(employeeInResponse.getId() != null && employeeInResponse.getId() != 0);

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testUpdateEmployee() {

		String firstName = "Tom";
		String lastName = "Greg";
		String updatedFirstName = "Tommy";
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);

		try {

			// rest call
			Employee createdEmployee = callCreateEmployee(employee);
			createdEmployee.setFirstName(updatedFirstName);

			// rest call
			Employee updatedEmployee = callUpdateEmployee(createdEmployee);

			assertTrue(updatedEmployee.getFirstName().equals(createdEmployee.getFirstName()));
			assertTrue(updatedEmployee.getLastName().equals(createdEmployee.getLastName()));
			assertTrue(updatedEmployee.getId().equals(createdEmployee.getId()));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	private Employee callCreateEmployee(Employee employee) {
		try {
			// Convert object to JSON
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonBody = objectMapper.writeValueAsString(employee);

			// create request
			HttpPost request = new HttpPost(restUrl + "/create-employee");
			request.setHeader("Content-Type", "application/json");
			request.setEntity(new StringEntity(jsonBody)); // Set JSON entity

			// get response
			try (CloseableHttpResponse response = client.execute(request)) {
				int statusCode = response.getStatusLine().getStatusCode();
				assertTrue(statusCode == HttpStatus.OK.value());

				String responseBody = EntityUtils.toString(response.getEntity());
				ObjectMapper responseObjectMapper = new ObjectMapper();
				Employee employeeInResponse = responseObjectMapper.readValue(responseBody, Employee.class);

				return employeeInResponse;
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		return null;
	}

	private Employee callUpdateEmployee(Employee employeeToUpdate) {
		try {
			// Convert object to JSON
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonBody = objectMapper.writeValueAsString(employeeToUpdate);

			// create request
			HttpPut request = new HttpPut(restUrl + "/update-employee");
			request.setHeader("Content-Type", "application/json");
			request.setEntity(new StringEntity(jsonBody)); // Set JSON entity

			// get response
			try (CloseableHttpResponse response = client.execute(request)) {
				int statusCode = response.getStatusLine().getStatusCode();
				assertTrue(statusCode == HttpStatus.OK.value());

				String responseBody = EntityUtils.toString(response.getEntity());
				ObjectMapper responseObjectMapper = new ObjectMapper();
				Employee employeeInResponse = responseObjectMapper.readValue(responseBody, Employee.class);

				return employeeInResponse;
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		return null;
	}

	@Test
	public void testGetEmployees() {

		// employee 1
		Employee employee1 = new Employee();
		employee1.setFirstName("Tom");
		employee1.setLastName("Greg");

		// employee 2
		Employee employee2 = new Employee();
		employee2.setFirstName("Sandra");
		employee2.setLastName("David");

		try {
			// rest call
			callCreateEmployee(employee1);
			callCreateEmployee(employee2);

			List<Employee> employees = getEmployees();

			assertTrue(employees.size() == 2);

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	private List<Employee> getEmployees() {
		try {
			// create request
			HttpGet request = new HttpGet(restUrl + "/get-employees");

			// get response
			try (CloseableHttpResponse response = client.execute(request)) {
				int statusCode = response.getStatusLine().getStatusCode();
				assertTrue(statusCode == HttpStatus.OK.value());

				String responseBody = EntityUtils.toString(response.getEntity());
				ObjectMapper responseObjectMapper = new ObjectMapper();
				List<Employee> employees = responseObjectMapper.readValue(responseBody,
						new TypeReference<List<Employee>>() {
						});

				return employees;
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

		return null;
	}

	@Test
	public void testDeleteEmployee() {

		// employee 1
		Employee employee1 = new Employee();
		employee1.setFirstName("Tom");
		employee1.setLastName("Greg");

		try {
			// rest call
			Employee createdEmployee = callCreateEmployee(employee1);

			// create request
			HttpDelete request = new HttpDelete(restUrl + "/delete-employee/" + createdEmployee.getId());

			// get response
			try (CloseableHttpResponse response = client.execute(request)) {
				int statusCode = response.getStatusLine().getStatusCode();
				assertTrue(statusCode == HttpStatus.OK.value());

				List<Employee> employees = getEmployees();
				assertTrue(employees.size() == 0);

			}
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
