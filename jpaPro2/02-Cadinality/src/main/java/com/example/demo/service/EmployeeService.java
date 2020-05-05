package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class EmployeeService {

    protected EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Employee createEmployee(String name, long salary) {
        Employee emp = new Employee();
        emp.setName(name);
        emp.setSalary(salary);

        em.persist(emp);
        return emp;
    }

    public Employee setEmployeeDepartment(int empId, int deptId) {
        Employee emp = em.find(Employee.class, empId);
        Department dept = em.find(Department.class, deptId);
        dept.addEmployee(emp);
        return emp;
    }

    public List<Employee> findAllEmployees() {
        Query query = em.createQuery("select e from Employee e");
        return query.getResultList();
    }

}
