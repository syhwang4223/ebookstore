package hello.ebookstore.controller;

import hello.ebookstore.domain.Cart;
import hello.ebookstore.domain.Member;
import hello.ebookstore.dto.MemberResponseDto;
import hello.ebookstore.exception.LoginFailException;
import hello.ebookstore.service.CartService;
import hello.ebookstore.service.MemberService;
import hello.ebookstore.dto.MemberSignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;



    /**
     * 전체 회원 조회
     */
//        @GetMapping
//        public List<Member> findAllMembers() {
//            return memberService.findMembers();
//        }

    /**
     * 현재 로그인 중인 회원 정보 조회
     */
    @GetMapping("/myInfo")
    public ResponseEntity<MemberResponseDto> getMyInfo() {
        return ResponseEntity.ok(memberService.getMyInfo());
    }



}
