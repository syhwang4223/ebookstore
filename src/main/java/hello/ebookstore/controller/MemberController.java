package hello.ebookstore.controller;

import hello.ebookstore.dto.*;
import hello.ebookstore.entity.CartItem;
import hello.ebookstore.entity.Member;
import hello.ebookstore.exception.ResponseMessage;
import hello.ebookstore.jwt.UserAdapter;
import hello.ebookstore.service.CartService;
import hello.ebookstore.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final CartService cartService;


    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        Member member = memberService.signup(signUpRequestDto);
        return new ResponseEntity<>(new MemberResponseDto(member), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("mainController.login");
        return ResponseEntity.ok(memberService.login(loginRequestDto));
    }


    @PostMapping("/validate/login-id")
    public ResponseEntity<ResponseMessage> validateLoginId(@RequestBody Map<String, String> loginId) {
        Boolean exist = memberService.isExistLoginId(loginId.get("loginId"));
        String message = exist ? "이미 사용중인 아이디입니다" : "사용 가능한 아이디입니다";

        return ResponseEntity.ok(new ResponseMessage(message));
    }

    @PostMapping("/validate/email")
    public ResponseEntity<ResponseMessage> validateEmail(@RequestBody Map<String, String> email) {
        Boolean exist = memberService.isExistEmail(email.get("email"));
        String message = exist ? "이미 사용중인 이메일 주소입니다" : "사용 가능한 이메일 주소입니다";

        return ResponseEntity.ok(new ResponseMessage(message));
    }


    /**
     * 현재 로그인 중인 회원 정보 조회
     */
    @GetMapping("/myInfo")
    public ResponseEntity<MemberResponseDto> getMyInfo() {
        return ResponseEntity.ok(memberService.getMyInfo());
    }


    /**
     * 카트 기능
     */

    // 카트에 책 담기
    @PostMapping("/cart/{bookId}")
    public String addToCart(@AuthenticationPrincipal UserAdapter adapter, @PathVariable Long bookId) {
        Member loginMember = adapter.getMember();
        CartItem savedItem = cartService.addToCart(bookId, loginMember);
        return "ok";
    }

    // 카트에 있는 책인지 판별
    @GetMapping("/cart/check/{bookId}")
    public Map<String, Boolean> isExistInCart(@AuthenticationPrincipal UserAdapter adapter, @PathVariable Long bookId) {
        Member loginMember = adapter.getMember();
        Boolean exist = cartService.isExistInCart(bookId, cartService.getCart(loginMember));

        Map<String, Boolean> result = new HashMap<>();
        result.put("exist", exist);
        return result;
    }


    // 카트에 담긴 책의 총 갯수 반환
    @GetMapping("/cart/totalCount")
    public Map<String, Integer> getBookCountInCart(@AuthenticationPrincipal UserAdapter adapter) {
        Member loginMember = adapter.getMember();
        int totalCount = cartService.getCart(loginMember).getTotalCount();

        Map<String, Integer> result = new HashMap<>();
        result.put("totalBooksCountInCart", totalCount);
        return result;
    }

    // 카트에서 책 삭제
    @DeleteMapping("/cart/{bookId}")

    public String outFromCart(@AuthenticationPrincipal UserAdapter adapter, @PathVariable Long bookId) {
        Member loginMember = adapter.getMember();
        cartService.outFromCart(bookId, loginMember);
        return "ok";
    }

    // 카트에 담긴 책 목록 조회
    @GetMapping("/cart")
    public CartDto getCart(@AuthenticationPrincipal UserAdapter adapter) {
        Member loginMember = adapter.getMember();
        return new CartDto(cartService.getCart(loginMember));
    }


}
