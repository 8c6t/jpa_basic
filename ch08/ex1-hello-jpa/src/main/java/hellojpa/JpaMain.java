package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("hello1");
            em.persist(member1);

            Member member2 = new Member();
            member1.setUsername("hello2");
            em.persist(member2);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());
//            Member findMember = em.getReference(Member.class, member.getId());
//            System.out.println("before findMember = " + findMember.getClass());
//            System.out.println("findMember.username = " + findMember.getUsername());
//            System.out.println("after findMember = " + findMember.getClass());

//            Member m1 = em.find(Member.class, member1.getId());
////            Member m2 = em.find(Member.class, member2.getId());
//            Member m2 = em.getReference(Member.class, member2.getId());
//            System.out.println("m1: " + m1.getClass());
////            logic(m1, m2);
//
//            Member reference = em.getReference(Member.class, member1.getId());
//            System.out.println("Reference: " + reference.getClass());
//
//            System.out.println("a == a: " + (m1 == reference));

//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember = " + refMember.getClass()); // Proxy
//
//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember = " + findMember.getClass()); //Member
//
//            System.out.println("refMember == findMember: " + (refMember == findMember));

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass()); // Proxy
            // refMember.getUsername();
            Hibernate.initialize(refMember);

            System.out.println("isLoaded: " + emf.getPersistenceUnitUtil().isLoaded(refMember));

//            em.detach(refMember);
//            em.close();
            em.clear();

            refMember.getUsername();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void logic(Member m1, Member m2) {
        // System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()) );
        System.out.println("m1 == m2; " + (m1 instanceof Member));
        System.out.println("m1 == m2; " + (m2 instanceof Member));
    }

}
