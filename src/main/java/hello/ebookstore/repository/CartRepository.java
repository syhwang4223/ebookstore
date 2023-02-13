package hello.ebookstore.repository;

import hello.ebookstore.entity.Cart;
import hello.ebookstore.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMember(Member member);
}
