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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeImportService{
     
    @Autowired
    private MongoTemplate mongoTemplate;
    
    

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
                Employee employee = new Employee();

                Cell nameCell = row.getCell(0);
                if (nameCell != null) {
                    employee.setName(nameCell.getStringCellValue());
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
                    	System.out.println("Non-numeric value found for age in row " + row.getRowNum());
                    }
                }

                Cell dateCell = row.getCell(3);
                if (dateCell != null) {
                    if (dateCell.getCellType() == CellType.NUMERIC) {
                        LocalDate localDate = dateCell.getLocalDateTimeCellValue().toLocalDate();
                        employee.setDateOfJoining(localDate);
                    } else {
                        // Handle non-numeric cell value
                    	System.out.println("Non-numeric value found for age in row " + row.getRowNum());
                    }
                }
 

                employees.add(employee);
            }
        }

        mongoTemplate.insertAll(employees);
    }

    
}
