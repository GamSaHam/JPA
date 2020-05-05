package com.example.demo.client.examples;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@SpringBootTest
public class TestServices {
    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;


    @Test
    public void deplaymentTests(){
        EntityManager em = emf.createEntityManager();
        departmentService.setEm(em);
        employeeService.setEm(em);

        em.getTransaction().begin();
        Department resultDepartment = departmentService.createDepartment("개발팀");
        em.getTransaction().commit();

        List<Department> departments = departmentService.findAllDepartments();

        departments.forEach( department->{
            System.out.println(department);
        } );

        em.getTransaction().begin();
        Employee employee = employeeService.createEmployee("함감사", 2400000);
        Employee employee2 = employeeService.setEmployeeDepartment(employee.getId(), resultDepartment.getId());

        em.persist(employee2);

        em.getTransaction().commit();


        List<Employee> employees = employeeService.findAllEmployees();

        employees.forEach(_employee -> {
            System.out.println(_employee);
        });

        em.close();
        emf.close();

    }


}
