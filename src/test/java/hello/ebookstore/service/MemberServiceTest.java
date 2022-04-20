package hello.ebookstore.service;

import hello.ebookstore.dto.SignUpRequestDto;
import hello.ebookstore.entity.Cart;
import hello.ebookstore.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired CartService cartService;

    @Test
    public void 회원가입시_멤버의_카트생성() throws Exception{
        // given
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("loginId", "password", "password", "email@email.com");
        Member member = memberService.signup(signUpRequestDto);

        // when
        Cart findCart = cartService.getCart(member);

        // then
        Assertions.assertThat(findCart.getMember()).isEqualTo(member);

    }
}