package com.example.demo;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManagerFactory;


@SpringBootTest
class DemoApplicationTests {


    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Test
    void addReviewAndCourses(){

        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }

        System.out.println("finished");
//        SessionFactory sessionFactory =  entityManagerFactory.unwrap(SessionFactory.class);
//        Session session = sessionFactory.openSession();
//
//        session.beginTransaction();
//
//        // create a course
//
//        Course course = new Course("Pacman - How to Score One Million Points");
//
//        System.out.println("Saving the course...");
//        session.save(course);
//        System.out.println("Saved the course:" + course);
//
//        // create the students
//        Student student1 = new Student("Join", "Deo", "john@test.com");
//        Student student2 = new Student("Mary", "Public", "mary@test.com");
//
//        course.addStudent(student1);
//        course.addStudent(student2);
//
//        System.out.println("Saving the students...");
//        session.save(student1);
//        session.save(student2);
//        System.out.println("Saved the students:" + course.getStudents());
//
//
//        session.getTransaction().commit();
    }






}
