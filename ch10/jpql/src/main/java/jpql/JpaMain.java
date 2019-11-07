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
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();


            List<Member> result = em.createQuery("SELECT m FROM Member m", Member.class)
                    .getResultList();

            Member findMember = result.get(0);
            findMember.setAge(20);

            // JOIN
            List<Team> join = em.createQuery("SELECT t FROM Member m JOIN m.team t", Team.class)
                    .getResultList();

            // Embedded Type
            List<Address> embeddedType = em.createQuery("SELECT o.address FROM Order o", Address.class)
                    .getResultList();

            // Scala
            List<Object[]> queryList = em.createQuery("SELECT DISTINCT m.username, m.age FROM Member m")
                    .getResultList();

            Object[] queryResult = queryList.get(0);
            System.out.println("username = " + queryResult[0]);
            System.out.println("age = " + queryResult[1]);

            // new
            List<MemberDTO> newList = em.createQuery("SELECT NEW jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = newList.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());

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
