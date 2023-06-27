package com.inoptra.employeedepartmentdemo.controllers;

import com.inoptra.employeedepartmentdemo.services.DepartmentService;
import com.inoptra.employeedepartmentdemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;


/**
 * @Author: Shrikrishna Prabhumirashi
 * @Description:
 * Supports Account keeping for Employees working under department
 * Below are few operations supported by this controller
 *  - Get total salary to be paid to a department
 *  - Get total salary to be paid to all departments
 *  - Get average salary to be paid to a department
 *  - Get average salary to be paid to all departments
 **/
@Controller
@RequestMapping("/account/accountstats")
public class AccountStatisticsController {
	@Autowired
	EmployeeService employeeService;

	@Autowired
	DepartmentService departmentService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountStatisticsController.class);

	@GetMapping("/all/total")
	@ResponseBody
	public Map<Long, Double> getTotalSalaryForAllDepartments() {
		return departmentService.getSalaryForAllDepartments();
	}

	@GetMapping("/{deptId}/total")
	@ResponseBody
	public double getTotalSalaryForDepartment(@PathVariable("deptId") Long id) {

		try {
			return departmentService.getSalaryForDepartment(id);
		}
		catch (Exception ex) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage()
			);
		}
	}

	@GetMapping("/all/avg")
	@ResponseBody
	public Map<Long, Double> getAverageSalaryForAllDepartments(){
		return departmentService.getAverageSalaryForAllDepartments();
	}

	@GetMapping("/{deptId}/avg")
	@ResponseBody
	public double getAverageSalaryForDepartment(@PathVariable("deptId") Long id) {
		try {
			return departmentService.getAverageSalaryForDepartment(id);
		}
		catch (Exception ex) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage()
			);
		}
	}

}
