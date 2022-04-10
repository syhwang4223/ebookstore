package hello.ebookstore.controller;

import hello.ebookstore.domain.CartItem;
import hello.ebookstore.dto.LoginRequestDto;
import hello.ebookstore.dto.MemberResponseDto;
import hello.ebookstore.dto.SignUpRequestDto;
import hello.ebookstore.dto.TokenDto;
import hello.ebookstore.repository.MemberRepository;
import hello.ebookstore.service.BookService;
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
    private final BookService bookService;


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


    // 카트에 책 담기
    @PostMapping("/cart/{bookId}")
    public void addToCart(@PathVariable Long bookId) {
        bookService.addToCart(bookId);
    }

    // 카트에 담긴 책 목록 조회
    @GetMapping("/cart")
    public List<CartItemDto> getCart() {
        return bookService.getCart().stream()
                .map(CartItemDto::new)
                .collect(Collectors.toList());
    }

    @Getter
    static class CartItemDto {

        private String title;
        private String author;
        private int price;

        public CartItemDto(CartItem cartItem) {
            title = cartItem.getBook().getTitle();
            author = cartItem.getBook().getAuthor();
            price = cartItem.getBook().getPrice();
        }
    }


}
