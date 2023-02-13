package hello.ebookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

/**
 * 로그인 시에 사용.
 */

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

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
