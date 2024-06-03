package com.example.employeemanagement.controller;


import java.io.IOException;






import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.service.EmployeeImportService;
import com.example.employeemanagement.service.EmployeeService;


import jakarta.servlet.http.HttpServletResponse;




@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	

	
	@Autowired
    private EmployeeImportService employeeImportService;
	
	@Autowired EmployeeRepository employeeRepository;
	
	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		
		//checking if the employeeID is null or not
		if (employee.getEmployeeID() == null) {
            employee.setEmployeeID("");
        }
		
		if (employee.getExperience() == null) {
            employee.setExperience("");
        }
		
		//added employee
		return employeeService.createEmployee(employee);
	}
	
	@PutMapping("/id")
	public void updateEmployeeID() {
		List<Employee> employees = employeeRepository.findAll();
		for (Employee employee: employees) {
			if(employee.getEmployeeID() == null || employee.getEmployeeID() == "") {
				employee.setEmployeeID(employeeService.createEmployeeID(employee));
				employeeRepository.save(employee);
			}
			else {
				continue;
			}
		}	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
		employeeService.calculateEmployeeExperience();
		 Optional<Employee> employee = employeeService.getEmployeeById(id);
	        return employee.map(ResponseEntity::ok)
	                       .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping
	public List<Employee> getAllEmployees() {
		employeeService.calculateEmployeeExperience();
		return employeeService.getAllEmployees();
	}
	
	@PutMapping("/{id}")
	 public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employeeDetails) {
		employeeService.calculateEmployeeExperience();
		Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteEmployeeWithoutID() {
		List<Employee> employees = employeeService.getAllEmployees();
		for(Employee employee: employees) {
				if(employee.getDateOfJoining() == null) {
				employeeService.deleteEmployee(employee.getId());
			}
		}
		 return ResponseEntity.noContent().build();
    }
	
	//searching for employees
	@GetMapping("/search")
	public List<Employee> searchEmployee(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer age, 
			@RequestParam(required = false) Double salary,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) String employeeId) {
		employeeService.calculateEmployeeExperience();
		return employeeService.searchEmployees(name, age, salary, startDate, endDate);
	}
	
	
	//IMPORT DATA FROM EXCEL FILE
	@PostMapping("/import")
    public ResponseEntity<String> importEmployees(@RequestPart("file") MultipartFile file) {
        try {
            employeeImportService.importEmployeesFromExcel(file);
            return ResponseEntity.ok("Employees imported successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error importing employees: " + e.getMessage());
        }
    }
	
	@GetMapping("/export")
    public void exportEmployees(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        employeeService.calculateEmployeeExperience();
        employeeService.exportEmployeesToExcel(response.getOutputStream());
    }
}
