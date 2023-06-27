package com.inoptra.employeedepartmentdemo.controllers;

import com.inoptra.employeedepartmentdemo.exceptions.DepartmentNotFoundException;
import com.inoptra.employeedepartmentdemo.models.Department;
import com.inoptra.employeedepartmentdemo.models.Employee;
import com.inoptra.employeedepartmentdemo.services.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @GetMapping("/get/{id}")
    @ResponseBody
    public Department getDepartment(@PathVariable("id") Long id) {
        try {
            return departmentService.getDepartment(id);
        }
        catch (DepartmentNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
            );
        }
    }

    @PostMapping("/create")
    @ResponseBody
    public Department createDepartment(@RequestBody Department d) {
        return departmentService.createDepartment(d);
    }

    @PostMapping("/update")
    @ResponseBody
    public Department updateDepartment(@RequestBody Department d) {
        try {
            return departmentService.updateDepartment(d);
        }
        catch (DepartmentNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Department deleteDepartment(@PathVariable("id") Long id) {
        try {
            return departmentService.deleteDepartment(id);
        }
        catch (DepartmentNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage()
            );
        }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
            );
        }
    }
}
