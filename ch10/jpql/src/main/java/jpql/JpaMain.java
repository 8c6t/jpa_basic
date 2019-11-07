package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member1 = new Member();
            member1.setUsername("관리자1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();

            // concat
            String concatQuery = "SELECT CONCAT('a', 'b') FROM Member m";
            // SUBSTRING
            String substrQuery = "SELECT SUBSTRING(m.username, 2, 3) FROM Member m";
            // LOCATE
            String locateQuery = "SELECT LOCATE('de', 'abcdefg') FROM Member m";
            // SIZE
            String sizeQuery = "SELECT SIZE(t.members) FROM Team t";
            // INDEX
            String indexQuery = "SELECT INDEX(t.members) FROM Team t";
            // CUSTOM FUNCTION
            String funcQuery = "SELECT FUNCTION('group_concat', m.username) FROM Member m";
            String hibernateFuncQuery = "SELECT group_concat(m.username) FROM Member m";

            List<String> result = em.createQuery(hibernateFuncQuery, String.class).getResultList();
            // List<Integer> result = em.createQuery(indexQuery, Integer.class).getResultList();

            for (String s: result) {
                System.out.println("s = " + s);
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
}
