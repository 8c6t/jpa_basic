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
            member.setUsername("관리자");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            // case
            String caseQuery = "SELECT " +
                                "CASE WHEN m.age <= 10 THEN '학생요금' " +
                                "     WHEN m.age >= 60 THEN '경로요금' " +
                                "     ELSE '일반요금' " +
                                "END " +
                            "FROM Member m";

            // coalesce
            String coalesceQuery = "SELECT COALESCE(m.username, '이름 없는 회원') as username FROM Member m";

            // nullif
            String nullIfQuery = "SELECT NULLIF(m.username, '관리자') as username FROM Member m";

            List<String> result = em.createQuery(nullIfQuery, String.class)
                    .getResultList();

            for (String s : result) {
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
