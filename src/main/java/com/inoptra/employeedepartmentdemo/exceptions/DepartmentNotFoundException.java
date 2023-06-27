package com.inoptra.employeedepartmentdemo.exceptions;

/**
 * @Author: Shrikrishna Prabhumirashi
 * @Description:
 * Represents business exception thrown when department not found in the context
 **/
public class DepartmentNotFoundException extends Exception {
    public DepartmentNotFoundException(String msg) {
        super(msg);
    }
}
