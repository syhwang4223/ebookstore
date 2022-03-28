package hello.ebookstore.repository;

import hello.ebookstore.domain.Cart;
import hello.ebookstore.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final EntityManager em;

    public void save(Cart cart) {
        em.persist(cart);
    }

    public Cart findOne(Long id) {
        return em.find(Cart.class, id);
    }

}
