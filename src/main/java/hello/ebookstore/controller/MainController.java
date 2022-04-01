package hello.ebookstore.controller;

import hello.ebookstore.dto.*;
import hello.ebookstore.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public MemberResponseDto signup(@Valid @RequestBody MemberSignUpDto memberSignUpDto) {
        return memberService.signup(memberSignUpDto);
    }

    @PostMapping("/login")
    public TokenDto login(@Valid @RequestBody MemberLoginDto memberLoginDto) {
        log.info("mainController.login");
        return memberService.login(memberLoginDto);
    }

    @PostMapping("/reissue")
    public TokenDto reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return memberService.reissue(tokenRequestDto);
    }

    @GetMapping("/hello")
    public String test() {
        return "hello";
    }
    @GetMapping("/hello2")
    public String test2() {
        return "hello2";
    }
}
