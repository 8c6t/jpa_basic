package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // primaryCache(em);
            // afterSelect(em);
            // writeBehind(em);
            // dirtyChecking(em);

            flush(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void flush(EntityManager em) {
        Member member = new Member(200L, "member200");
        em.persist(member);

        em.flush();
        System.out.println("==================");
    }

    // 1차 캐시
    private static void primaryCache(EntityManager em) {
        Member member = new Member();
        member.setId(101L);
        member.setName("HelloJPA");

        Member findMember = em.find(Member.class, 101L);
        System.out.println("findMember.id = " + findMember.getId());
        System.out.println("findMember.name = " + findMember.getName());

        System.out.println("=== BEFORE ===");
        em.persist(member);
        System.out.println("=== AFTER ===");
    }

    // 동일성 보장
    private static void equality(EntityManager em) {
        Member findMember1 = em.find(Member.class, 101L);
        Member findMember2 = em.find(Member.class, 101L);

        System.out.println("result = " + (findMember1 == findMember2));
    }

    // 쓰기 지연
    private static void writeBehind(EntityManager em) {
        Member member1 = new Member(150L, "A");
        Member member2 = new Member(160L, "B");
        System.out.println("================");

        em.persist(member1);
        em.persist(member2);
    }

    // 변경 감지
    private static void dirtyChecking(EntityManager em) {
        Member member = em.find(Member.class, 150L);
        member.setName("ZZZZ");
        System.out.println("==============");
    }

}
