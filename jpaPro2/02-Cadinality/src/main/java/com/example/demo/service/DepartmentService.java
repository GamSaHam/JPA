package com.example.demo.service;


import com.example.demo.model.Department;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Data
@Service
public class DepartmentService {

    private EntityManager em;

    public Department createDepartment(String name) {

        Department dept = new Department();
        dept.setName(name);
        em.persist(dept);

        return dept;
    }

    public List<Department> findAllDepartments() {
        TypedQuery query = em.createQuery("select d from Department d", Department.class);

        return query.getResultList();
    }


}
