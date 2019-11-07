package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

/*
            String query = "SELECT t.members.size FROM Team t";
//             List result = em.createQuery(query, Collection.class).getResultList();
//                        for (Object o: result) {
//                System.out.println("s = " + o);
//            }
            Integer result = em.createQuery(query, Integer.class).getSingleResult();
            System.out.println("result = " + result);
*/

            // with join
            String joinQuery = "SELECT m.username FROM Team t JOIN t.members m";
            List<Collection> joinResult = em.createQuery(joinQuery, Collection.class).getResultList();

            System.out.println("join result = " + joinResult);

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
