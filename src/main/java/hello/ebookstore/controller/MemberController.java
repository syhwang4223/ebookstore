package hello.ebookstore.controller;

import hello.ebookstore.domain.Member;
import hello.ebookstore.service.MemberService;
import hello.ebookstore.DTO.MemberFormDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/register")
    public Member register(@RequestBody MemberFormDTO form) {
        Member member = new Member(form.getLoginId(), form.getPassword(), form.getEmailAddress());
        log.info(member.toString());
        return member;
    }

    @GetMapping("/{memberId}")
    public Member member(@PathVariable Long memberId, Model model) {
        return memberService.findOne(memberId);
    }

    @GetMapping
    public List<Member> list() {
        return memberService.findMembers();
    }

}
