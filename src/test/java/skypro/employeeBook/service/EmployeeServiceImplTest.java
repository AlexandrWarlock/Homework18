package skypro.employeeBook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import skypro.employeeBook.dto.Employee;
import skypro.employeeBook.exception.EmployeeAlreadyAddedException;
import skypro.employeeBook.exception.EmployeeStorageIsFullException;
import skypro.employeeBook.exception.NotFoundException;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {
    private EmployeeServiceImpl underTest;
    @BeforeEach
    void beforeEach () {
        underTest = new EmployeeServiceImpl();
    }
    Employee expectedEmployee = new Employee("Alex", "M", 4, 50_000);
    @Test
    void addEmployee_shouldAddEmployeeToMapAndReturn() {

        Employee result = underTest.addEmployee(expectedEmployee.getFirstname(), expectedEmployee.getLastname(),
                expectedEmployee.getDepartment(), expectedEmployee.getSalary());
        assertTrue(underTest.findAll().contains(expectedEmployee));
        assertEquals(expectedEmployee, result);
    }
    @Test
    void addEmployee_shouldThrowExceptionWhenNotEnoughSizeMap() {
        for (int i = 0; i < 5; i++) {
            underTest.addEmployee((expectedEmployee.getFirstname() + i),
                    (expectedEmployee.getLastname() +i),
                    expectedEmployee.getDepartment(), expectedEmployee.getSalary());
        }
        assertThrows(EmployeeStorageIsFullException.class, () -> underTest.addEmployee(expectedEmployee.getFirstname(),
                expectedEmployee.getLastname(), expectedEmployee.getDepartment(), expectedEmployee.getSalary()));
    }
    @Test
    void addEmployee_shouldThrowExceptionWhenEqualEmployeeInMap() {
        underTest.addEmployee((expectedEmployee.getFirstname()),
                (expectedEmployee.getLastname()),
                expectedEmployee.getDepartment(), expectedEmployee.getSalary());
        assertThrows(EmployeeAlreadyAddedException.class, () -> underTest.addEmployee((expectedEmployee.getFirstname()),
                (expectedEmployee.getLastname()),
                expectedEmployee.getDepartment(), expectedEmployee.getSalary()));
    }

    @Test
    void removeEmployee_shouldAddEmployeeAndRemoveExist() {
        Employee result = underTest.addEmployee((expectedEmployee.getFirstname()),
                (expectedEmployee.getLastname()),
                expectedEmployee.getDepartment(), expectedEmployee.getSalary());
        underTest.removeEmployee(expectedEmployee.getFirstname(), expectedEmployee.getLastname());
        assertNotNull(result);
        assertEquals(expectedEmployee, result);
    }

    @Test
    void removeEmployee_shouldThrowExceptionWhenEmployeeIsNull() {
        assertThrows(NotFoundException.class, () -> underTest.removeEmployee(expectedEmployee.getFirstname(),
                expectedEmployee.getLastname()));
    }

    @Test
    void getEmployee_WhenEmployeeExist() {
        underTest.addEmployee((expectedEmployee.getFirstname()),
                (expectedEmployee.getLastname()),
                expectedEmployee.getDepartment(), expectedEmployee.getSalary());
        Employee result = underTest.getEmployee(expectedEmployee.getFirstname(), expectedEmployee.getLastname());
        underTest.getEmployee(expectedEmployee.getFirstname(), expectedEmployee.getLastname());
        assertNotNull(result);
        assertEquals(expectedEmployee, result);
    }

    @Test
    void getEmployee_WhenEmployeeDoesNotExist() {
        assertThrows(NotFoundException.class, () -> underTest.getEmployee(expectedEmployee.getFirstname(),
                expectedEmployee.getLastname()));
    }
    @Test
    void findAll_shouldReturnEmployeesListInMap() {
        Employee employee = new Employee("James", "Bomd", 1, 40_111);
        underTest.addEmployee(expectedEmployee.getFirstname(), expectedEmployee.getLastname(),
                expectedEmployee.getDepartment(),expectedEmployee.getSalary());
        underTest.addEmployee(employee.getFirstname(), employee.getLastname(), employee.getDepartment(),
                employee.getSalary());
        Collection<Employee> result = underTest.findAll();
        assertTrue(result.containsAll(List.of(expectedEmployee, employee)));
    }

    @Test
    void findAll_shouldReturnEmptyListWhenEmployeeNotInMap() {
        Collection<Employee> all = underTest.findAll();
        assertTrue(all.isEmpty());
    }

}