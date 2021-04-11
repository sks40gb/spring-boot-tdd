package com.demo.tdd.controller;

import com.demo.tdd.beans.Employee;
import com.demo.tdd.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("api/v1/employee/{id}")
    public Employee getEmployee(@PathVariable("id") Long id) {
        return this.employeeService.getById(id);
    }
}

