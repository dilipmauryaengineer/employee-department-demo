package com.inoptra.employeedepartmentdemo.services;

import java.util.Optional;

import com.inoptra.employeedepartmentdemo.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import com.inoptra.employeedepartmentdemo.models.Employee;
import com.inoptra.employeedepartmentdemo.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

/**
 * @Author: Shrikrishna Prabhumirashi
 * @Description:
 * Service layer contract which supports operations on Employee object
 **/
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * Compute and return salary for given employee
	 * @return Calculate salary of employee and return the same if employee with given @param empId exists
	 * @return 0.0, otherwise.
	 */
	public double getSalary(long empId) {

		Optional<Employee> optEmployee = employeeRepository.findById(empId);

		if(!optEmployee.isPresent()) return 0.0;

		Employee emp = optEmployee.get();

		return emp
				.getSalary()
				.getSalaryComponents()
				.parallelStream()
				.reduce(
						Double.valueOf(0.0),
						(total, sc) -> total + (sc.getFactor() *  emp.getSalary().getBaseSalary()),
						(c1, c2) -> c1 + c2
				);
	}

	public Employee getEmployee(Long id) throws EmployeeNotFoundException {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if (optionalEmployee.isPresent()) {
			return optionalEmployee.get();
		}
		else {
			throw new EmployeeNotFoundException("Employee does not exist");
		}
	}

	public Employee createEmployee(Employee e) {
		employeeRepository.save(e);
		return e;
	}

	public Employee updateEmployee(Employee e) throws EmployeeNotFoundException {
		boolean exists = employeeRepository.existsById(e.getId());

		if (exists) {
			employeeRepository.save(e);
			return e;
		}
		else {
			throw new EmployeeNotFoundException("Update not Supported: Employee does not exist");
		}


	}

	public Employee deleteEmployee(Long id) throws EmployeeNotFoundException {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);

		if (optionalEmployee.isPresent()) {
			employeeRepository.delete(optionalEmployee.get());
			return optionalEmployee.get();
		}
		else {
			throw new EmployeeNotFoundException("Delete not Supported: Employee does not exist " + id);
		}


	}
}
