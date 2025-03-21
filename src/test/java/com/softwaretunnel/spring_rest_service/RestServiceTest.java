package com.softwaretunnel.spring_rest_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

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

				assertTrue(employeeInResponse.getFirstName().equals(employee.getFirstName()));
				assertTrue(employeeInResponse.getLastName().equals(employee.getLastName()));
				assertTrue(employeeInResponse.getId() != null && employeeInResponse.getId() != 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
