package com.example.demo.client.examples.client;

import com.example.demo.model.examples.model.Employee;
import com.example.demo.model.examples.model.EmployeeService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class EmployeeTestTest {

    @Autowired
    public EmployeeService service;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void main() {


        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }

        SessionFactory sessionFactory =  entityManagerFactory.unwrap(SessionFactory.class);
        Session session = sessionFactory.openSession();



        EmployeeService service = new EmployeeService();
        service.setSession(session);


        //  create and persist an employee
        session.getTransaction().begin();
        Employee emp = service.createEmployee(158, "John Doe", 45000);
        session.getTransaction().commit();
        System.out.println("Persisted " + emp);


        // find a specific employee
        emp = service.findEmployee(158);
        System.out.println("Found " + emp);

        // find all employees
        Collection<Employee> emps = service.findAllEmployees();
        for (Employee e : emps)
            System.out.println("Found Employee: " + e);

        // update the employee
        session.getTransaction().begin();
        emp = service.raiseEmployeeSalary(158, 1000);
        session.getTransaction().commit();
        System.out.println("Updated " + emp);

        // remove an employee
        session.getTransaction().begin();
        service.removeEmployee(158);
        session.getTransaction().commit();
        System.out.println("Removed Employee 158");

        session.close();



//
//        // close the EM and EMF when done
//        em.close();
//        emf.close();
    }
}