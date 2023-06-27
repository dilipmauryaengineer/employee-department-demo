package com.inoptra.employeedepartmentdemo;

import com.inoptra.employeedepartmentdemo.exceptions.DepartmentNotFoundException;
import com.inoptra.employeedepartmentdemo.models.Department;
import com.inoptra.employeedepartmentdemo.services.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class DepartmentServiceTest {

    @Autowired
    DepartmentService departmentService;

    private static final Random random = new Random(1000000000);

    @Test
    public void testGetDepartment() throws DepartmentNotFoundException {
        Department d1 = new Department();
        d1.setName("Unit Test Dept-1");

        Department d2 = departmentService.createDepartment(d1);

        d2 = departmentService.getDepartment(d2.getId());

        assert (d1.getName().equals(d2.getName()) && d1.getId().equals(d2.getId()));
    }

    @Test
    public void testGetDepartmentFail() {
        Assertions.assertThrows(Exception.class, () -> departmentService.getDepartment(random.nextLong()));
    }

    @Test
    public void testCreateDepartment() {
        Department d1 = new Department();
        d1.setName("Unit Test Dept-1");

        Department d2 = departmentService.createDepartment(d1);
        assert (d1.getName().equals(d2.getName()));
    }

    @Test
    public void testUpdateDepartment() throws DepartmentNotFoundException {
        Department d1 = new Department();
        d1.setName("Unit Test Emp-1");

        d1 = departmentService.createDepartment(d1);
        d1.setName("Unit Test Dept-1, Updated");

        Department d2 = departmentService.updateDepartment(d1);

        assert (d2.getName().equals("Unit Test Dept-1, Updated"));
    }

    @Test
    public void testUpdateDepartmentFail() {

        Department d1 = new Department();
        d1.setId(random.nextLong());

        Assertions.assertThrows(DepartmentNotFoundException.class, () -> departmentService.updateDepartment(d1));
    }

    @Test
    public void testDeleteDepartment() throws DepartmentNotFoundException {
        Department d1 = new Department();
        d1.setName("Unit Test Dept-1");

        d1 = departmentService.createDepartment(d1);

        Department d2 = departmentService.deleteDepartment(d1.getId());

        assert (d2.getName().equals("Unit Test Dept-1"));
    }

    @Test
    public void testDeleteDepartmentFail() {
        Assertions.assertThrows(DepartmentNotFoundException.class, () -> departmentService.deleteDepartment(random.nextLong()));
    }
}
