package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query1 = em.createQuery("SELECT m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("SELECT m.username from Member m", String.class);
            Query query3 = em.createQuery("SELECT m.username, m.age from Member m");

            List<Member> resultList = query1.getResultList();

            for (Member member1: resultList) {
                System.out.println("member1: " + member);
            }

            // Spring Data JPA -> optional or null
            Member result = query1.getSingleResult();
            System.out.println("result: " + result);

            // Parameter Binding
            Member singleResult = em.createQuery("SELECT m FROM Member m WHERE m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("singleResult = " + singleResult.getUsername());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();

    }
}
