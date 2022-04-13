package hello.ebookstore.controller;

import hello.ebookstore.domain.CartItem;
import hello.ebookstore.dto.*;
import hello.ebookstore.service.CartService;
import hello.ebookstore.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final CartService cartService;


    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok(memberService.signup(signUpRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("mainController.login");
        return ResponseEntity.ok(memberService.login(loginRequestDto));
    }


    /**
     * 현재 로그인 중인 회원 정보 조회
     */
    @GetMapping("/myInfo")
    public ResponseEntity<MemberResponseDto> getMyInfo() {
        return ResponseEntity.ok(memberService.getMyInfo());
    }


    /**
     *  카트 기능
     */
    
    // 카트에 책 담기
    @PostMapping("/cart/{bookId}")
    public String addToCart(@PathVariable Long bookId) {
        cartService.addToCart(bookId);
        return "ok";
    }

    // 카트에서 책 삭제
    @DeleteMapping("/cart/{bookId}")
    public String outFromCart(@PathVariable Long bookId) {
        cartService.outFromCart(bookId);
        return "ok";
    }

    // 카트에 담긴 책 목록 조회
    @GetMapping("/cart")
    public CartDto getCart() {
        return new CartDto(cartService.getCart());
    }




}
