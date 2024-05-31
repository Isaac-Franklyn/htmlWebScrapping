package com.example.employeemanagement.service;








import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.EmployeeRepositoryCustom;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    @Qualifier("employeeRepositoryCustomImpl")
    private EmployeeRepositoryCustom employeeRepositoryCustom;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(String id, Employee employeeDetails) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setName(employeeDetails.getName());
            employee.setAge(employeeDetails.getAge());
            employee.setSalary(employeeDetails.getSalary());
            employee.setDateOfJoining(employeeDetails.getDateOfJoining());
            return employeeRepository.save(employee);
        } else {
            return null;
        }
    }

    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }
    
    //SEARCH FOR EMPLOYEES
    public List<Employee> searchEmployees(String name, Integer age, Double salary, LocalDate startDate, LocalDate endDate) {
        return employeeRepositoryCustom.searchEmployees(name, age, salary, startDate, endDate);
    }
    
 // Export employees to Excel
    public void exportEmployeesToExcel(OutputStream outputStream) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Employees");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Age");
            headerRow.createCell(3).setCellValue("Salary");
            headerRow.createCell(4).setCellValue("Date of Joining");

            // Get employee data
            List<Employee> employees = getAllEmployees();

            // Populate data rows
            int rowNum = 1;
            for (Employee employee : employees) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(employee.getId());
                row.createCell(1).setCellValue(employee.getName());
                row.createCell(2).setCellValue(employee.getAge());
                row.createCell(3).setCellValue(employee.getSalary());
                row.createCell(4).setCellValue(employee.getDateOfJoining().toString());
            }

            // Write workbook to output stream
            workbook.write(outputStream);
        }
    }
    
}

