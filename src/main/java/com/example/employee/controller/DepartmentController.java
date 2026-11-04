package com.example.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.model.EmployeeModel;
import com.example.employee.repository.EmployeeRepository;


@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/api/v1/")
public class EmployeeController {
	@Autowired
	private EmployeeRepository emprepo;
	
	@GetMapping("/employees")
    public List<EmployeeModel> getAllStudents()
    {
        return emprepo.findAll();
    }
	
	@PostMapping("/employees")
    public EmployeeModel createEmployee(@RequestBody EmployeeModel employee) {
        return emprepo.save(employee);
    }
	
	@GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeModel> getStudentById(@PathVariable Long id) {
        EmployeeModel employee = emprepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("employee not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }
	
	
	@PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeModel> updateStudent(@PathVariable Long id, @RequestBody EmployeeModel employeeDetails){
		EmployeeModel employee = emprepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employee.setName(employeeDetails.getName());
        employee.setAddress(employeeDetails.getAddress());
        employee.setGender(employeeDetails.getGender());
        employee.setPhone(employeeDetails.getPhone());
        
        EmployeeModel updatedEmployee = emprepo.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }
	
	@DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        EmployeeModel employee = emprepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        emprepo.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
