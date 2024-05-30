package com.example.employeemanagement.repository;

import java.util.ArrayList;

import java.util.List;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.example.employeemanagement.model.Employee;

@Component
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Employee> searchEmployees(String name, Integer age, Double salary, LocalDate startDate, LocalDate endDate) {
        Criteria criteria = new Criteria();
        List<Criteria> criteriaList = new ArrayList<>();

        if (name != null) {
            criteriaList.add(Criteria.where("name").is(name));
        }
        if (age != null) {
            criteriaList.add(Criteria.where("age").is(age));
        }
        if (salary != null) {
            criteriaList.add(Criteria.where("salary").is(salary));
        }
        if (startDate != null && endDate != null) {
            criteriaList.add(Criteria.where("dateOfJoining").gte(startDate).lte(endDate));
        } else if (startDate != null) {
            criteriaList.add(Criteria.where("dateOfJoining").gte(startDate));
        } else if (endDate != null) {
            criteriaList.add(Criteria.where("dateOfJoining").lte(endDate));
        }

        if (!criteriaList.isEmpty()) {
            criteria.andOperator(criteriaList.toArray(new Criteria[0]));
        }

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Employee.class);
    }

}