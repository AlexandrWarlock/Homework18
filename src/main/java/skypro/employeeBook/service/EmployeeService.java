package skypro.employeeBook.service;

import skypro.employeeBook.dto.Employee;

import java.util.Collection;

public interface EmployeeService {
    Employee addEmployee(String firstname, String lastname);

    Employee remoteEmployee(String firstname, String lastname);

    Employee getEmployee(String firstname, String lastname);

    Collection<Employee> findAll();
}
