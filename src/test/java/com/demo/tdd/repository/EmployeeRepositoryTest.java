package com.demo.tdd.repository;

import com.demo.tdd.beans.Employee;
import static org.hamcrest.MatcherAssert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findById_shouldReturnEmployee(){

        //given
        Employee john = new Employee();
        john.setName("John");
        Employee savedJohn = entityManager.persistAndFlush(john);

        //when
        Employee employee = employeeRepository.findById(1L).orElse(null);

        //then
        assertThat(john, is(equalTo(savedJohn)));
        assertThat(john.getId(), is(equalTo(1L)));
        assertThat(john.getName(), is(equalTo("John")));
    }


}
