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

            String inner = "SELECT m FROM Member m JOIN m.team t";
            String outer = "SELECT m FROM Member m LEFT OUTER JOIN m.team t";
            String theta = "SELECT m from Member m, Team t WHERE m.username = t.name";

            // on
            String joinOn = "SELECT m FROM Member m JOIN m.team t ON t.name = 'teamA'";
            String outerOn = "SELECT m FROM Member m LEFT JOIN Team t ON m.username = t.name";

            List<Member> result = em.createQuery(outerOn, Member.class)
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
