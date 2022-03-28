package hello.ebookstore.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter @Setter
@ToString
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String emailAddress;


    public Member(String loginId, String password, String emailAddress) {
        this.loginId = loginId;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    protected Member() {

    }

}
