package hello.ebookstore.service;

import hello.ebookstore.domain.Member;
import hello.ebookstore.domain.RefreshToken;
import hello.ebookstore.dto.*;
import hello.ebookstore.exception.DuplicateException;
import hello.ebookstore.exception.InvalidRequestException;
import hello.ebookstore.exception.NoAuthenticationException;
import hello.ebookstore.jwt.TokenProvider;
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
        Member member = memberSignUpDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
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
        // 1. loginId/password 를 기반으로 Authentication 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberLoginDto.toAuthentication();

        // 2. 실제로 검증이 이루어지는 부분
        //    입력받은 비밀번호가 등록된 비밀번호와 일치하는지 확인한다
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행된다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);
        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        log.info("토큰발급");
        // 5. 토큰 발급
        return tokenDto;

    }

    /**
     * 토큰 재발행
     */
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {

        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new NoAuthenticationException("Refresh Token 이 유효하지 않습니다");
        }

        // 2. Access Token 에서 MemberId 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        
        // 3. 저장소에서 MemberId 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new InvalidRequestException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 이 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new NoAuthenticationException("토큰의 유저 정보가 일치하지 않습니다");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    /**
     * 현재 로그인한 회원 정보 조회
     * (현재 SecurityContext에 있는 유저 정보 가져오기)
     */
    public MemberResponseDto getMyInfo() {
        return memberRepository.findOne(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new InvalidRequestException("유저 정보가 없습니다."));
    }



    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


}
