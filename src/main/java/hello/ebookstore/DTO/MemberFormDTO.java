package hello.ebookstore.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberFormDTO {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    private String emailAddress;

}
