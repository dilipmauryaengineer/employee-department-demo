package com.inoptra.employeedepartmentdemo.controllers;

import com.inoptra.employeedepartmentdemo.exceptions.EmployeeNotFoundException;
import com.inoptra.employeedepartmentdemo.models.Employee;
import com.inoptra.employeedepartmentdemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping("/get/{id}")
    @ResponseBody
    public Employee getEmployee(@PathVariable("id") Long id) {
        try {
            return employeeService.getEmployee(id);
        }
        catch (EmployeeNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }
    }

    @PostMapping("/create")
    @ResponseBody
    public Employee createEmployee(@RequestBody Employee e) {
        return employeeService.createEmployee(e);
    }

    @PostMapping("/update")
    @ResponseBody
    public Employee updateEmployee(@RequestBody Employee e) {
        try {
            return employeeService.updateEmployee(e);
        }
        catch (EmployeeNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Employee deleteEmployee(@PathVariable("id") Long id) {
        try {
            return employeeService.deleteEmployee(id);
        }
        catch (EmployeeNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }
    }
}
