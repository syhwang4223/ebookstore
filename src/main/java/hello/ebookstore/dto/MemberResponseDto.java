package hello.ebookstore.dto;

import hello.ebookstore.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String loginId;
    private String emailAddress;
    private LocalDateTime joinDate;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getLoginId(), member.getEmailAddress(), member.getJoinDate());
    }

}
