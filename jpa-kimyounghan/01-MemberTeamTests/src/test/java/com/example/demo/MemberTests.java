package com.example.demo;

import com.example.demo.model.Member;
import com.example.demo.model.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@SpringBootTest
public class MemberTests {

    @Autowired
    EntityManagerFactory emf;

    @Test
    public void memberCrudTests(){
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {
            tx.begin();

            Team team = new Team();
            team.setName("야구팀");

            em.persist(team);

            Member member = new Member();
            member.setName("홍길동");
            member.setTeam(team);

            em.persist(member);


            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();

        }finally {
            em.close();
        }


        em.close(); //엔티티 매니저 종료
    }


    @Test
    public void getMemeberTest(){
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        Member member = em.find(Member.class, 2L);

        System.out.println(member.getTeam().toString());


        em.close();


    }

    @Test
    public void updateMemberTest(){
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        em.getTransaction().begin();

        Team team = new Team();
        team.setName("유도부");

        em.persist(team);

        Member member = em.find(Member.class, 1L);
        member.setTeam(team);


        em.getTransaction().commit();

        em.close();

    }

    @Test
    public void removeTeamTest(){

        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        em.getTransaction().begin();

        Member member = em.find(Member.class, 1L);
        member.setTeam(null);

        Team team = em.find(Team.class, 3L);

        em.remove(team);


        em.getTransaction().commit();

        em.close();


    }

    @Test
    public void directionTests(){

        EntityManager em = emf.createEntityManager();


        Team team = em.find(Team.class, 1L);

        List<Member> memberList = team.getMemberList();

        System.out.println(memberList);


        em.close();


    }

}
