package com.inoptra.employeedepartmentdemo.exceptions;

/**
 * @Author: Shrikrishna Prabhumirashi
 * @Description:
 * Represents business exception thrown when employee not found in the context
 **/
public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String msg) {
        super(msg);
    }
}
