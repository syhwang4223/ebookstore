package hello.ebookstore.service;

import hello.ebookstore.entity.*;
import hello.ebookstore.exception.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired EntityManager em;
    @Autowired CartService cartService;
    @Autowired MemberService memberService;

    @Test
    public void 카트에_책_담기() throws Exception{
        // given
        Member member = new Member("testMember", "asdf", "sadf@asfd", Authority.ROLE_USER);
        Cart cart = new Cart();
        cart.setMember(member);

        em.persist(member);
        em.persist(cart);

        // when
        cartService.addToCart(1L, member);

        // then
        Cart findCart = cartService.getCart(member);
        assertThat(findCart.getCartItems().size()).isEqualTo(1);
        assertThat(cartService.isExistInCart(1L, findCart)).isEqualTo(true);
        assertThat(cartService.isExistInCart(2L, findCart)).isEqualTo(false);


    }
    @Test
    public void 이미_카트에_있는_책이면_에러() throws Exception{
        // given
        Member member = new Member("testMember", "asdf", "sadf@asfd", Authority.ROLE_USER);
        Cart cart = new Cart();
        cart.setMember(member);

        em.persist(member);
        em.persist(cart);

        cartService.addToCart(1L, member);

        // when


        // then
        assertThrows(BadRequestException.class, () ->{
            cartService.addToCart(1L, member);
        });

    }
    
    @Test
    public void 카트에서_책_빼기() throws Exception{
        // given
        Member member = new Member("testMember", "asdf", "sadf@asfd", Authority.ROLE_USER);
        Cart cart = new Cart();
        cart.setMember(member);

        em.persist(member);
        em.persist(cart);

        cartService.addToCart(1L, member);

        // when
        cartService.outFromCart(1L, member);

        // then
        Cart findCart = cartService.getCart(member);
        assertThat(findCart.getCartItems().size()).isEqualTo(0);
    }
    
    @Test
    public void 카트에_존재하지않는_책을_빼려하면_에러() throws Exception {
        // given
        Member member = new Member("testMember", "asdf", "sadf@asfd", Authority.ROLE_USER);
        Cart cart = new Cart();
        cart.setMember(member);

        em.persist(member);
        em.persist(cart);

        // when

        // then
        assertThrows(BadRequestException.class, () -> {
            cartService.outFromCart(1L, member);
        });
    }
    
    
}