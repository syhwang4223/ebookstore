package hello.ebookstore.dto;

import hello.ebookstore.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String loginId;
    private String emailAddress;
    private LocalDateTime joinDate;

    public MemberResponseDto(Member member) {
        id = member.getId();
        loginId = member.getLoginId();
        emailAddress = member.getEmailAddress();
        joinDate = member.getJoinDate();
    }

}
