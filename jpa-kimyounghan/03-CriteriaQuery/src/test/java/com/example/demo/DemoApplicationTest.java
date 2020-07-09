package com.example.demo;

import com.example.demo.domain.Address;
import com.example.demo.domain.Member;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTest extends TestCase {

    @Autowired
    EntityManager em;

    @Test
    public void addMember() {


        Member member = new Member();
        member.setName("홍길동");

        Address address = new Address();
        address.setZipcode("0001");
        address.setStreet("봉천동");
        address.setCity("서울");

        member.setAddress(address);
        em.getTransaction().begin();
        em.persist(member);
        em.getTransaction().commit();



    }

    @Test
    public void printCriteriaBuilder() {



        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        Root<Member> m = query.from(Member.class);

        CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("name"), "홍길동"));

        List<Member> resultList = em.createQuery(cq).getResultList();

        System.out.println(resultList);



    }

    @Test
    public void printQTypedQuery() {


        TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);

        List<Member> resultList = query.getResultList();
        for(Member member : resultList){
            System.out.println("member" + member);
        }



    }

    @Test
    public void printQuery(){


        Query query = em.createQuery("select m.name, m.address from Member m");

        List resultList = query.getResultList();

        for(Object o : resultList){
            Object[] result = (Object[]) o;
            System.out.println("name" + result[0]);
            System.out.println("address" + result[1]);
        }


    }


    @Test
    public void parameterBindingTest(){



        TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);

        query.setFirstResult(1);
        query.setMaxResults(1);
        List<Member> resultList = query.getResultList();

        resultList.stream().forEach(member -> System.out.println(member));



    }


    // COUNT, MAX, MIN, AVG, SUM
    // GROUP BY, HAVING
    // ORDER BY
    // ASC(DEFAULT) DESC

    @Test
    public void joinTest(){


        // LEFT JOIN
        // RIGHT JOIN
        String query = "select m from Member m INNER JOIN m.orders o where m.name = :name";
//        String query = "select m from Member m INNER JOIN m.orders o on m.name = "홍길동" where m.name = :name";

        // Patch Join
        // select m from Member m, Orders o where m.name = o.name

        List<Member> members = em.createQuery(query, Member.class).setParameter("name", "홍길동").getResultList();

        System.out.println(members);


    }

    //  AND OR NOT

    // > | | >= | < | <= | <>

    // BETWEEN , IN, LIKE, NULL
    // where is null

    // IS [NOT] EMPTY
    @Test
    public void collectionStatementTest(){

        TypedQuery<Member> query = em.createQuery("select m from Member m where m.orders is not empty ", Member.class);

        List<Member> resultList = query.getResultList();

        System.out.println(resultList);


    }

    // 문자 함수
    // CONCAT, SUBSTRING, TRIM, LOWER, UPPER, LENGTH, LOCATE

    // 숫자 함수
    // ABS, SQRT, MOD, SIZE, INDEX

    // 날짜 함수
    // CURRENT_DATE, CURRENT_TIME, CURRENT_TIMESTAMP

    @Test
    public void caseExampleTest(){


        Query query = em.createQuery("select case when m.name = '홍길동' then '홍길동 맞음' else '다른사람' end from Member m");

        List resultList = query.getResultList();

        resultList.stream().forEach(o -> {

            System.out.println(o);

        });


    }

    @Test
    public void criteriaQueryTest(){



        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> m = cq.from(Member.class);
        cq.select(m);

        TypedQuery<Member> query = em.createQuery(cq);
        List<Member> members = query.getResultList();

        System.out.println(members);

    }

    @Test
    public void addWhereCaseTest(){

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> m = cq.from(Member.class);

        Predicate usernameEqual = cb.equal(m.get("name"), "홍길동");
        Predicate ageGt = cb.greaterThan(m.<Integer>get("age"), 10);


        Order nameDesc = cb.desc(m.get("name"));

        cq.select(m).where(usernameEqual).where(ageGt).orderBy(nameDesc);

        List<Member> members = em.createQuery(cq).getResultList();

        System.out.println(members.get(0).getAddress());
    }

    @Test
    public void multiSelectTest(){

        CriteriaBuilder cb = em.getCriteriaBuilder();


        CriteriaQuery<Object []> cq = cb.createQuery(Object[].class);

        Root<Member> m = cq.from(Member.class);

        cq.multiselect(m.get("name"), m.get("age")).distinct(true);
//        cq.multiselect(m.get("name"), m.get("age"));

        List<Object []> resultList = em.createQuery(cq).getResultList();

        for(Object[] objs : resultList){
            System.out.println((String)objs[0]);
            System.out.println((int)objs[1]);
        }

    }

    @Test
    public void tupleTest(){

        CriteriaBuilder cb = em.getCriteriaBuilder();


        CriteriaQuery<Tuple> cq = cb.createTupleQuery();

        Root<Member> m = cq.from(Member.class);

        cq.multiselect(m.get("name").alias("name"), m.get("age").alias("age")).distinct(true);
//        cq.multiselect(m.get("name"), m.get("age"));

        List<Tuple> resultList = em.createQuery(cq).getResultList();

        for(Tuple tuple : resultList){
            String name = tuple.get("name", String.class);
            int age = tuple.get("age", Integer.class);

            System.out.println(name + ", " + age);
        }

    }

    @Test
    public void groupByTest(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<Member> m = cq.from(Member.class);

        Expression maxAge = cb.max(m.<Integer>get("age"));

        cq.multiselect(m.get("name"), maxAge)
                .groupBy(m.get("name"))
                .having(cb.gt(maxAge, 10));


        TypedQuery<Object[]> query = em.createQuery(cq);
        List<Object[]> resultList = query.getResultList();

        for(Object[] objs: resultList){
            System.out.println((String)objs[0]);
            System.out.println((int)objs[1]);
        }

    }

    @Test
    public void criteriaJoinTest() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> m = cq.from(Member.class);
        //Join<Experiment,Assay> experimentAssays = entity_.join( entity_.get("assay_id") );
        Join<Member, com.example.demo.domain.Order> t = m.join("orders", JoinType.INNER);


        cq.select(m).distinct(true);

        TypedQuery<Member> ct = em.createQuery(cq);

    }

    @Test
    public void criteriaInTest(){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);

        cq.select(m).where(cb.in(m.get("name")).value("홍길동").value("길길동"));

        TypedQuery<Member> query = em.createQuery(cq);

        List<Member> resultList = query.getResultList();

        System.out.println(resultList);
    }

    @Test
    public void criteriaCaseTest(){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Object []> cq = cb.createQuery(Object[].class);
        Root<Member> m = cq.from(Member.class);

        cq.multiselect(m.get("name"), cb.selectCase().when(cb.ge(m.<Integer>get("age"), 20), "adult").when(cb.le(m.<Integer>get("age"), 10), "young").otherwise("others"));

        TypedQuery<Object []> query = em.createQuery(cq);

        List<Object []> resultList = query.getResultList();

        for(Object[] result : resultList){

            System.out.println((String)result[0]);
            System.out.println((String)result[1]);
        }

    }

    @Test
    public void setParameterTest(){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);

        cq.select(m).where(cb.equal(m.get("name"), cb.parameter(String.class, "nameParam")));

        List<Member> resultList = em.createQuery(cq).setParameter("nameParam", "홍길동").getResultList();

        System.out.println(resultList);

    }

    @Test
    public void nativeFunction() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Member> m = cq.from(Member.class);

        Expression<Long> function = cb.function("SUM", Long.class, m.get("age"));
        cq.select(function);


    }

}