package com.example.employeemanagement.repository;

import java.util.List;

import java.time.LocalDate;
import com.example.employeemanagement.model.Employee;

public interface EmployeeRepositoryCustom {
    List<Employee> searchEmployees(String name, Integer age, Double salary, LocalDate startDate, LocalDate endDate);
}
