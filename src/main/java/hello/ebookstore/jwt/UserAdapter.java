package hello.ebookstore.jwt;

import hello.ebookstore.entity.Member;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
@Slf4j
public class UserAdapter extends User {

    private final Member member;

    public UserAdapter(Member member) {
        super(member.getLoginId(), member.getPassword(), List.of(new SimpleGrantedAuthority(member.getAuthority().toString())));
        this.member = member;
    }

}
