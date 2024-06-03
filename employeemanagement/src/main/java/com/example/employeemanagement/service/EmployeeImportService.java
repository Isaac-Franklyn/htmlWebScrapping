package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeImportService{
     
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private EmployeeService employeeService;
    

    public void importEmployeesFromExcel(MultipartFile file) throws IOException {
        List<Employee> employees = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Assuming first row is header
            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                Row row = rows.next();
                
             // Check if the row is completely empty
                boolean isRowEmpty = true;
                for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                    if (row.getCell(i) != null && row.getCell(i).getCellType() != CellType.BLANK) {
                        isRowEmpty = false;
                        break;
                    }
                }
                
                if (isRowEmpty) {
                    continue;
                }
                
                Employee employee = new Employee();

                Cell nameCell = row.getCell(0);
                if (nameCell != null) {
                	if (nameCell.getCellType() == CellType.STRING) {
          
                		 employee.setName(nameCell.getStringCellValue());
                	}
                	else {
                		System.out.println("Non-String value found for age in row " + row.getRowNum());
                	}
                }

                Cell ageCell = row.getCell(1);
                if (ageCell != null) {
                    if (ageCell.getCellType() == CellType.NUMERIC) {
                        employee.setAge((int) ageCell.getNumericCellValue());
                    } else {
                        // Handle non-numeric cell value
                    	System.out.println("Non-numeric value found for age in row " + row.getRowNum());
                    }
                }

                Cell salaryCell = row.getCell(2);
                if (salaryCell != null) {
                    if (salaryCell.getCellType() == CellType.NUMERIC) {
                        employee.setSalary(salaryCell.getNumericCellValue());
                    } else {
                        // Handle non-numeric cell value
                    	 System.out.println("Non-numeric value found for salary in row " + row.getRowNum());                    }
                }

                Cell dateCell = row.getCell(3);
                if (dateCell != null) {
                    if (dateCell.getCellType() == CellType.STRING) {
                        try {
                            LocalDate localDate = LocalDate.parse(dateCell.getStringCellValue());
                            employee.setDateOfJoining(localDate);
                        } catch (DateTimeParseException e) {
                            // Handle invalid date format
                            System.out.println("Invalid date format in row " + row.getRowNum());
                        }
                    } else {
                        // Handle non-string cell value
                        System.out.println("Non-string value found for date in row " + row.getRowNum());
                    }
                }
                
                employee.setEmployeeID(employeeService.createEmployeeID(employee));
              
                employees.add(employee);
            }
        }

        mongoTemplate.insertAll(employees);
    }

    
}
