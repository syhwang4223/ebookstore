package hello.ebookstore.dto;

import hello.ebookstore.domain.Authority;
import hello.ebookstore.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 로그인 시에 사용.
 */

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginDto {

    @NotBlank
    private String loginId;
    @NotBlank
    private String password;

//    public Member toMember(PasswordEncoder passwordEncoder) {
//        return Member.builder()
//                .password(passwordEncoder.encode(password))
//                .build();
//    }


    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(loginId, password);
    }

}
