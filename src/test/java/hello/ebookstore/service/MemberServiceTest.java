package hello.ebookstore.service;

import hello.ebookstore.domain.Member;
import hello.ebookstore.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = createMember();

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();
        Assertions.assertEquals(member, memberService.findOne(savedId));
    }

    private Member createMember() {
        Member member = new Member("testId", "testPwd", "test@email.com");
        return member;
    }

    @Test
    public void 이미_존재하는_아이디() throws Exception {
        //given

        //when

        //then
    }

    @Test
    public void 로그인아이디로_회원찾기() throws Exception {
        //given
        Member member = createMember();
        memberService.join(member);

        //when
        Member findMember = memberService.findByLoginId(member.getLoginId());

        //then
        assertThat(member.getLoginId()).isEqualTo(findMember.getLoginId());
    }

}