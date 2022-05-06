package hello.ebookstore.service;

import hello.ebookstore.entity.Cart;
import hello.ebookstore.entity.Member;
import hello.ebookstore.dto.*;
import hello.ebookstore.exception.BadRequestException;
import hello.ebookstore.exception.DuplicateException;
import hello.ebookstore.exception.LoginFailException;
import hello.ebookstore.jwt.TokenProvider;
import hello.ebookstore.repository.CartRepository;
import hello.ebookstore.repository.MemberRepository;
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
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    /**
     * 회원 가입
     */
    @Transactional
    public Member signup(@Valid SignUpRequestDto signUpRequestDto){
        validateDuplicateMember(signUpRequestDto);
        validatePasswordConfirm(signUpRequestDto);
        Member member = signUpRequestDto.toMember(passwordEncoder);
        Cart cart = Cart.createCart(member);

        memberRepository.save(member);
        cartRepository.save(cart);

        return member;
    }

    private void validatePasswordConfirm(SignUpRequestDto signUpRequestDto) {
        if (!signUpRequestDto.getPassword().equals(signUpRequestDto.getPasswordConfirm())) {
            throw new BadRequestException("비밀번호를 다시 확인해주세요");
        }
    }

    private void validateDuplicateMember(SignUpRequestDto signUpRequestDto) {
        Optional<Member> byLoginId = memberRepository.findByLoginId(signUpRequestDto.getLoginId());
        Optional<Member> byEmail = memberRepository.findByEmail(signUpRequestDto.getEmailAddress());
        if (byLoginId.isPresent() | byEmail.isPresent()) {
            throw new DuplicateException("중복 아이디 또는 이메일이 존재합니다.");
        }

    }

    public Boolean isExistLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }

    public Boolean isExistEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    /**
     * 로그인
     */
    @Transactional
    public TokenDto login(@Valid LoginRequestDto loginRequestDto) {
        log.info("memberService.login");

        // 아이디, 비밀번호 확인
        Member member = memberRepository.findByLoginId(loginRequestDto.getLoginId()).orElseThrow(() -> new LoginFailException("가입되지 않은 아이디입니다."));
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new LoginFailException("비밀번호가 틀렸습니다");
        }


        // 1. loginId/password 를 기반으로 Authentication 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행된다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성

        return tokenProvider.createToken(authentication);

    }


}
