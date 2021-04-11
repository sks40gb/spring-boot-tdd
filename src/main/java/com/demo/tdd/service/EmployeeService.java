package com.demo.tdd.service;

import com.demo.tdd.beans.Employee;
import com.demo.tdd.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
}
