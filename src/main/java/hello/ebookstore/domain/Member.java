package hello.ebookstore.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    @Column(unique = true)
    private String emailAddress;

    private LocalDateTime joinDate;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();


    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setMember(this);
    }

    @Builder
    public Member(String loginId, String password, String emailAddress, Authority authority) {
        this.loginId = loginId;
        this.password = password;
        this.emailAddress = emailAddress;
        this.authority = authority;
        joinDate = LocalDateTime.now();
    }

    protected Member() {

    }

}
