package com.example.employeemanagement.service;

import java.io.IOException;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
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
    
    public String createEmployeeID(Employee employee) {
    	//creating the EmployeeID
    	boolean isBlank = employee.getEmployeeID() == "";
    	boolean isEmpty = employee.getEmployeeID() == null;
    	if(employee.getDateOfJoining() != null && ( isEmpty|| isBlank))
    	{
    		String dateString = employee.getDateOfJoining().toString();
        	String yearString = dateString.substring(2, 4);
        	String monthString = dateString.substring(5, 7);
        	String monthYearNew = monthString.concat(yearString);
        	int count = 1;
        	final String intial = "EMP";
        	
        	//checking to see if any employees exist in the same month and year and updating the counter
        	List<Employee> employees = employeeRepository.findAll();
        	for (Employee employee1: employees) {
        		String id = employee1.getEmployeeID();
        		if(id != null) {
        			String monthYear = id.substring(4, 8);
            		if(monthYearNew.equals(monthYear)) {
            			count++;
            		}
        		}
        		else {
        			break;
        		}
        		
        		}
        	
        	//making the counter into string and for numbers 1-9 -> 01-09, and for above 10, normal
        	String counter = "";
        	if (count >= 1 && count <= 9) {
        	    counter = String.format("%02d", count);
        	} else{
        	    counter = "10";
        	}
        	return (intial.concat("_").concat(monthYearNew).concat("_").concat(counter));
    	}
    	else
    		return employee.getEmployeeID();
    }
    
 // Method to calculate employee experience for all employees.
    public void calculateEmployeeExperience() {
        LocalDate today = LocalDate.now();
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            LocalDate joiningDate = employee.getDateOfJoining();
            long yearsOfExperience = ChronoUnit.YEARS.between(joiningDate, today);
            
            
         // Calculate months of experience separately
            LocalDate anniversaryDate = joiningDate.plusYears(yearsOfExperience);
            long monthsOfExperience = ChronoUnit.MONTHS.between(anniversaryDate, today);

            // Combine years and months into a single string
            String experience = yearsOfExperience + "." + monthsOfExperience;

            // Update employee's experience
            employee.setExperience(experience);
            // Save updated employee
            employeeRepository.save(employee);
        }
    }
   
    public Employee createEmployee(Employee employee) {
    	//setting up employee ID
    	employee.setEmployeeID(createEmployeeID(employee));
    	
    	//save it to employee repository
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
            employee.setEmployeeID(createEmployeeID(employee));
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
            headerRow.createCell(5).setCellValue("Employee ID");
            headerRow.createCell(6).setCellValue("Experience");

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
                row.createCell(5).setCellValue(employee.getEmployeeID());
                calculateEmployeeExperience();
                row.createCell(6).setCellValue(employee.getExperience());
                
            }

            // Write workbook to output stream
            workbook.write(outputStream);
        }
    }
    
 

    // Schedule the method to run every morning at 00:00
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleEmployeeExperienceCalculation() {
        calculateEmployeeExperience();
    }
    
}

