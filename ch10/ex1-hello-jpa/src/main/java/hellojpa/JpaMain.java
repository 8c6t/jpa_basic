package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // jpql(em);
            // criteria(em);

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            // em.flush();

            // flush -> commit, query
            // dbconn.executeQuery("select * from member");

            List<Member> resultList = em.createNativeQuery("SELECT MEMBER_ID, city, street, zipcode, USERNAME from MEMBER", Member.class)
                    .getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1: " + member1);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void criteria(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        Root<Member> m = query.from(Member.class);

        CriteriaQuery<Member> cq = query.select(m);

        String username = "asdfqwer";
        if (username != null) {
            cq = cq.where(cb.equal(m.get("username"), "kim"));
        }

        List<Member> resultList = em.createQuery(cq).getResultList();
    }

    private static void jpql(EntityManager em) {
        List<Member> result = em.createQuery(
                "SELECT m FROM Member m WHERE m.username LIKE '%kim%'",
                Member.class
        ).getResultList();
    }

}
