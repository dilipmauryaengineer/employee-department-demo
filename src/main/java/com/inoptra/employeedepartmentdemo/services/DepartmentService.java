package com.inoptra.employeedepartmentdemo.services;

import com.inoptra.employeedepartmentdemo.exceptions.DepartmentNotFoundException;
import com.inoptra.employeedepartmentdemo.models.Department;
import com.inoptra.employeedepartmentdemo.models.Employee;
import com.inoptra.employeedepartmentdemo.models.SalaryComponent;
import com.inoptra.employeedepartmentdemo.repositories.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Shrikrishna Prabhumirashi
 * @Description:
 * Service layer contract which supports operations on Department object
 **/
@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    public Department getDepartment(Long id) throws DepartmentNotFoundException{
        Optional<Department> optionalDepartment =  departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            return optionalDepartment.get();
        }
        else {
            throw new DepartmentNotFoundException("Department not found " + id);
        }
    }

    public Department createDepartment(Department d) {
        departmentRepository.save(d);
        return d;
    }

    public Department updateDepartment(Department d) throws DepartmentNotFoundException{
        Optional<Department> optionalDepartment =  departmentRepository.findById(d.getId());
        if (optionalDepartment.isPresent()) {
            departmentRepository.save(d);
            return d;
        }
        else {
            throw new DepartmentNotFoundException("Update not Supported: Department not found " + d.getId());
        }
    }

    /**
     *
     * TODO: Does deleting a dept implies that all employees in that dept are to be deleted??
     * TODO: Below implementation assumes Employees in the said dept are deleted before Department is deleted.
     */
    public Department deleteDepartment(Long id) throws DepartmentNotFoundException{
        Optional<Department> optionalDepartment =  departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            departmentRepository.deleteById(id);
            return optionalDepartment.get();
        }
        else {
            throw new DepartmentNotFoundException("Delete not Supported: Department not found " + id);
        }
    }

    /**
     * TODO: While this will work, a huge data set in DB will cause slowness to get the data down from DB to tomact.
     * TODO: In such situation, it is preferred to run a query in DB that will do the same job.
     *
     * -- SQL Query to get Total Salary group by Department, Note: TODO DB query optimizations --
     *
     * Select d.id, sum(s.base_salary * sc.factor)
     * from employee e, department d, salary s, salary_component sc, salary_salarycomponent ssc
     * where d.id = e.department_id
     * and e.salary_id = s.id
     * and s.id = ssc.salary_id
     * and ssc.salary_salarycomponentid = sc.id
     * group by d.id;
     */
    public Map<Long, Double> getSalaryForAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        Map<Long, List<Employee>> map = departments.stream()
                .collect(Collectors.toMap( (department -> department.getId()), (department -> department.getEmployees()) ) );


        Map<Long, Double> mapSalaries = new HashMap<Long, Double>();

        for (Long deptId: map.keySet()) {
            Double deptSalary = this.calculateSalaryByDepartment(map.get(deptId));
            mapSalaries.put(deptId, deptSalary);
        }

        LOGGER.info("{}",mapSalaries);
        return mapSalaries;
    }

    private Double calculateSalaryByDepartment(List<Employee> employees) {
        Double deptSalary = 0.0;
        for ( Employee employee : employees ) {
            Double empSalary = employee.getSalary().getSalaryComponents()
                    .stream()
                    .reduce(
                            0.0,
                            (Double total, SalaryComponent sc) -> total + ( sc.getFactor() * employee.getSalary().getBaseSalary() ),
                            (c1,c2) -> c1+c2
                    );

            deptSalary = deptSalary + empSalary;
        }

        return deptSalary;
    }

    public Double getSalaryForDepartment(Long id) throws DepartmentNotFoundException{
        Department d = this.getDepartment(id);
        return this.calculateSalaryByDepartment(d.getEmployees());
    }

    public Double getAverageSalaryForDepartment(Long id) throws Exception {
        Department d = this.getDepartment(id);
        if (d.getEmployees().size() > 0) {
            return this.calculateSalaryByDepartment(d.getEmployees()) / d.getEmployees().size();
        }
        else {
            throw new DepartmentNotFoundException("Average Operation not Supported: Department has no employees " + id);
        }
    }

    public Map<Long, Double> getAverageSalaryForAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        Map<Long, List<Employee>> map = departments.stream()
                .collect(Collectors.toMap( (department -> department.getId()), (department -> department.getEmployees()) ) );



        Map<Long, Double> mapSalaries = new HashMap<Long, Double>();

        for (Long deptId: map.keySet()) {
            Double deptSalary = this.calculateSalaryByDepartment(map.get(deptId));
            //Avg. is only possible if there are employees in the dept
            if (map.get(deptId).size() > 0) {
                mapSalaries.put(deptId, deptSalary / map.get(deptId).size());
            }
        }

        LOGGER.info("{}",mapSalaries);
        return mapSalaries;
    }
}
