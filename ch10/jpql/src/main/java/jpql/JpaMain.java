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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "SELECT (SELECT AVG(m1.age) FROM Member m1) AS avgAge FROM Member m JOIN Team t ON m.username = t.name";

            String inlineView = "SELECT mm.age, mm.username " +
                                    "FROM (SELECT m.age, m.username FROM Member m) as mm";

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("result = " + result.size());

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
