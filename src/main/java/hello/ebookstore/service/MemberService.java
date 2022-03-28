package hello.ebookstore.service;

import hello.ebookstore.domain.Member;
import hello.ebookstore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(@Valid Member member){
        //validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByLoginId(member.getLoginId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    public Member findByLoginId(String loginId) {
        List<Member> findMembers = memberRepository.findByLoginId(loginId);
        if (findMembers.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        return findMembers.get(0);
    }

    /**
     * 회원 한명 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 로그인
     */
    public Member login(String loginId, String password) {
        List<Member> findMembers = memberRepository.findByLoginId(loginId);
        if (findMembers.isEmpty()){
            throw new IllegalStateException("존재하지 않는 아이디입니다.");
        } else if (!findMembers.get(0).getPassword().equals(password)) {
            throw new IllegalStateException("잘못된 비밀번호 입니다.");
        }

        /**
         * TO-DO
         * 로그인 로직 처리
         */


        return findMembers.get(0);
    }
}
