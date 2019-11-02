package com.altermetrik.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.altimetrik.Application;
import com.altimetrik.dto.EmployeeDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@Test
	public void createEmployee() throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:8080/api/v1/employees";
		URI uri = new URI(baseUrl);

		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setFirstName("B");
		employeeDTO.setEmpCode("CODE-x");
		employeeDTO.setLastName("B");
		employeeDTO.setEmailId("manubomma@gmail.com");

		ResponseEntity result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<EmployeeDTO>(employeeDTO),
				String.class);

		int status = result.getStatusCodeValue();
		assertEquals(201, status);

	}

	@Test
	public void getEmployeeList() throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:8080/api/v1/employees";
		URI uri = new URI(baseUrl);

		ResponseEntity result = restTemplate.getForEntity(uri, String.class);

		int status = result.getStatusCodeValue();
		assertEquals(200, status);
		String content = result.getBody().toString();
		EmployeeDTO[] employeeList = mapFromJson(content, EmployeeDTO[].class);
		assertTrue(employeeList.length > 0);
	}
}
