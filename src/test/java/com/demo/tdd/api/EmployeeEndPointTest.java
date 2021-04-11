package com.demo.tdd.api;

import com.demo.tdd.beans.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeEndPointTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getEmployeeById_shouldReturnEmployee(){
        //given

        //when
        ResponseEntity<Employee> responseEntity = testRestTemplate.getForEntity("/api/v1/employee/1", Employee.class);
        //then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getBody(), is(equalTo(new Employee(1L, "john"))));
    }

}

