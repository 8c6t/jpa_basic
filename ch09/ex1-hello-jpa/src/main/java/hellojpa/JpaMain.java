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
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=============== start ===============");
            Member findMember = em.find(Member.class, member.getId());

            // select(findMember);
            // update(findMember);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void update(Member findMember) {
        // homeCity -> newCity
        // findMember.getHomeAddress().setCity("newCity");
        Address a = findMember.getHomeAddress();
        findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

        // 치킨 -> 한식
        findMember.getFavoriteFoods().remove("치킨");
        findMember.getFavoriteFoods().add("한식");1

        findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "10000")); // equals 기반
        findMember.getAddressHistory().add(new AddressEntity("newCity1", "street", "10000"));
    }

//    private static void select(Member findMember) {
//        List<Address> addressHistory = findMember.getAddressHistory();
//        for (Address address : addressHistory) {
//            System.out.println("address = " + address.getCity());
//        }
//
//        Set<String> favoriteFoods = findMember.getFavoriteFoods();
//        for (String favoriteFood : favoriteFoods) {
//            System.out.println("favoriteFood = " + favoriteFood);
//        }
//    }

}
