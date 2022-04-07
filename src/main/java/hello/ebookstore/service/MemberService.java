package hello.ebookstore.service;

import hello.ebookstore.domain.Cart;
import hello.ebookstore.domain.Member;
import hello.ebookstore.domain.RefreshToken;
import hello.ebookstore.dto.*;
import hello.ebookstore.exception.DuplicateException;
import hello.ebookstore.exception.InvalidRequestException;
import hello.ebookstore.exception.LoginFailException;
import hello.ebookstore.exception.NoAuthenticationException;
import hello.ebookstore.jwt.TokenProvider;
import hello.ebookstore.repository.CartRepository;
import hello.ebookstore.repository.MemberRepository;
import hello.ebookstore.repository.RefreshTokenRepository;
import hello.ebookstore.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public MemberResponseDto signup(@Valid MemberSignUpDto memberSignUpDto){
        validateDuplicateMember(memberSignUpDto);
        validatePasswordConfirm(memberSignUpDto);
        Member member = memberSignUpDto.toMember(passwordEncoder);

        return MemberResponseDto.of(memberRepository.save(member));
    }

    private void validatePasswordConfirm(MemberSignUpDto memberSignUpDto) {
        if (!memberSignUpDto.getPassword().equals(memberSignUpDto.getPasswordConfirm())) {
            throw new RuntimeException("비밀번호를 다시 확인해주세요");
        }
    }

    private void validateDuplicateMember(MemberSignUpDto memberSignUpDto) {
        Optional<Member> byLoginId = memberRepository.findByLoginId(memberSignUpDto.getLoginId());
        Optional<Member> byEmail = memberRepository.findByEmail(memberSignUpDto.getEmailAddress());
        if (byLoginId.isPresent() | byEmail.isPresent()) {
            throw new DuplicateException("중복 아이디 또는 이메일이 존재합니다.");
        }

    }

    /**
     * 로그인
     */
    @Transactional
    public TokenDto login(@Valid MemberLoginDto memberLoginDto) {
        log.info("memberService.login");

        // 아이디, 비밀번호 확인
        Member member = memberRepository.findByLoginId(memberLoginDto.getLoginId()).orElseThrow(() -> new LoginFailException("가입되지 않은 아이디입니다."));
        if (!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())) {
            throw new LoginFailException("비밀번호가 틀렸습니다");
        }


        // 1. loginId/password 를 기반으로 Authentication 생성
//        UsernamePasswordAuthenticationToken authenticationToken = memberLoginDto.toAuthentication();
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행된다.
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.createToken(memberLoginDto.toAuthentication());

        // 3. 인증 정보를 기반으로 JWT 토큰 생성

//        return tokenProvider.createToken(authentication);

    }


    /**
     * 현재 로그인한 회원 정보 조회
     * (현재 SecurityContext에 있는 유저 정보 가져오기)
     */
    public MemberResponseDto getMyInfo() {
        return memberRepository.findOne(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }


}
