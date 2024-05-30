package com.example.employeemanagement.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.employeemanagement.model.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String>, EmployeeRepositoryCustom{
}