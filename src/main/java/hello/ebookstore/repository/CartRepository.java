package hello.ebookstore.repository;

import hello.ebookstore.entity.Cart;
import hello.ebookstore.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final EntityManager em;

    public Cart findByMember(Member member) {
        return em.createQuery("select c from Cart c where c.member = :member", Cart.class)
                .setParameter("member", member)
                .getSingleResult();
    }

    public void save(Cart cart) {
        em.persist(cart);
    }
}
