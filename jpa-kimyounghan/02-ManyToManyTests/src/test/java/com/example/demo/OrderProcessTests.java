package com.example.demo;

import com.example.demo.model.Member;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderProcessTests {

    @Autowired
    EntityManagerFactory emf;

    @Test
    public void orderSomethingTest(){

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Member member = new Member();
        member.setUserName("홍길동");
        em.persist(member);

        Product product = new Product();
        product.setName("맥북 프로 16인치");
        em.persist(product);

        Order order = new Order();
        order.setMember(member);
        order.setProduct(product);
        order.setOrderAmount(1);
        em.persist(order);

        em.getTransaction().commit();

        em.close();


    }

    @Test
    public void printOrders(){

        EntityManager em = emf.createEntityManager();

        Member member = em.find(Member.class, 1L);

        System.out.println(member.getOrders());

        member.getOrders().stream().forEach(order -> {
            System.out.println(order.getProduct());
        });

        em.close();

    }

}
