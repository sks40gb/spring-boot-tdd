package com.demo.tdd.controller;

import com.demo.tdd.beans.Employee;
import com.demo.tdd.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getEmployee_ofId_1_shouldReturnEmployee() throws Exception {

        Mockito.when(employeeService.getById(1L)).thenReturn(new Employee(1L, "john"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
            .andExpect(MockMvcResultMatchers.jsonPath("name").value("john"));
    }

    @Test
    public void getEmployee_ofId_2_shouldReturnEmployee() throws Exception {

        Mockito.when(employeeService.getById(2L)).thenReturn(new Employee(2L,"Alan"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/2"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("id").value(2L))
            .andExpect(MockMvcResultMatchers.jsonPath("name").value("Alan"));
    }


}
