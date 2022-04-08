package hello.ebookstore.controller;

import hello.ebookstore.dto.*;
import hello.ebookstore.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@Valid @RequestBody MemberSignUpDto memberSignUpDto) {
        return ResponseEntity.ok(memberService.signup(memberSignUpDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody MemberLoginDto memberLoginDto) {
        log.info("mainController.login");
        return ResponseEntity.ok(memberService.login(memberLoginDto));
    }


}
