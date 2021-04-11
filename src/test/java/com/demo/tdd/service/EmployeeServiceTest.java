package com.demo.tdd.service;

import com.demo.tdd.beans.Employee;
import com.demo.tdd.repository.EmployeeRepository;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void givenEmployeeId_shouldReturnEmployee(){
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee(1L, "John")));

        Assertions.assertEquals(new Employee(1L,"John"), employeeService.getById(1L));
    }

}
