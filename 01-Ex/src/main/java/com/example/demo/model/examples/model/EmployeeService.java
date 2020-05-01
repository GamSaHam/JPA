package com.example.demo.model.examples.model;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.Collection;

@Service
public class EmployeeService {

    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public Employee createEmployee(int id, String name, long salary) {
        Employee emp = new Employee(id);
        emp.setName(name);
        emp.setSalary(salary);
        session.persist(emp);
        return emp;
    }

    public void removeEmployee(int id) {
        Employee emp = findEmployee(id);
        if (emp != null) {
            session.remove(emp);
        }
    }

    public Employee raiseEmployeeSalary(int id, long raise) {
        Employee emp = session.find(Employee.class, id);
        if (emp != null) {
            emp.setSalary(emp.getSalary() + raise);
        }
        return emp;
    }

    public Employee findEmployee(int id) {
        return session.find(Employee.class, id);
    }

    public Collection<Employee> findAllEmployees() {
        TypedQuery query = session.createQuery("SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }
}
