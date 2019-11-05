package hellojpa;

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

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member1");
            member2.setTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

            // fetch(em, member);
            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void fetch(EntityManager em, Member member) {
        Member m = em.find(Member.class, member.getId());
        System.out.println("m = " + m.getTeam().getClass());

        System.out.println("=============================");
        System.out.println("teamName = " + m.getTeam().getName());
        System.out.println("=============================");
    }

}
