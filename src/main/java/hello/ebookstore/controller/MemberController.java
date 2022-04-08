package hello.ebookstore.controller;

import hello.ebookstore.domain.Book;
import hello.ebookstore.domain.CartItem;
import hello.ebookstore.domain.Member;
import hello.ebookstore.dto.MemberResponseDto;
import hello.ebookstore.exception.NoLoginMemberException;
import hello.ebookstore.repository.MemberRepository;
import hello.ebookstore.service.BookService;
import hello.ebookstore.service.MemberService;
import hello.ebookstore.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final BookService bookService;


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


    @GetMapping("/cart/{bookId}")
    public void addToCart(@PathVariable Long bookId) {
        bookService.addToCart(bookId);
    }

    @GetMapping("/cart")
    public List<CartItem> getCart() {
        return bookService.getCart();
    }

}
