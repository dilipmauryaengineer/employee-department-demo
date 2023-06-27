package com.inoptra.employeedepartmentdemo;

import com.inoptra.employeedepartmentdemo.exceptions.EmployeeNotFoundException;
import com.inoptra.employeedepartmentdemo.models.Employee;
import com.inoptra.employeedepartmentdemo.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class EployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    private static final Random random = new Random(1000000000);

    @Test
    public void testGetEmployee() throws EmployeeNotFoundException {
        Employee e1 = new Employee();
        e1.setName("Unit Test Emp-1");

        Employee e2 = employeeService.createEmployee(e1);

        e2 = employeeService.getEmployee(e2.getId());

        assert(e1.getName().equals(e2.getName()) && e1.getId().equals(e2.getId()));
    }
    @Test
    public void testGetEmployeeFail() {
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(random.nextLong()));
    }

    @Test
    public void testCreateEmployee() {
        Employee e1 = new Employee();
        e1.setName("Unit Test Emp-1");

        Employee e2 = employeeService.createEmployee(e1);
        assert(e1.getName().equals(e2.getName()));
    }

    @Test
    public void testUpdateEmployee() throws EmployeeNotFoundException{
        Employee e1 = new Employee();
        e1.setName("Unit Test Emp-1");

        e1 = employeeService.createEmployee(e1);
        e1.setName("Unit Test Emp-1, Updated");

        Employee e2 = employeeService.updateEmployee(e1);

        assert(e2.getName().equals("Unit Test Emp-1, Updated"));
    }

    @Test
    public void testUpdateEmployeeFail() throws EmployeeNotFoundException{

        Employee e1 = new Employee();
        e1.setId(random.nextLong());

        Assertions.assertThrows(Exception.class, () -> employeeService.updateEmployee(e1));
    }

    @Test
    public void testDeleteEmployee() throws EmployeeNotFoundException{
        Employee e1 = new Employee();
        e1.setName("Unit Test Emp-1");

        e1 = employeeService.createEmployee(e1);

        Employee e2 = employeeService.deleteEmployee(e1.getId());

        assert(e2.getName().equals("Unit Test Emp-1"));
    }

    @Test
    public void testDeleteEmployeeFail() throws EmployeeNotFoundException{
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(random.nextLong()));
    }
}
