package skypro.employeeBook.controller;

import org.springframework.web.bind.annotation.RestController;
import skypro.employeeBook.service.DepartmentService;

@RestController
public class DepartmentController {
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}
