package hello.ebookstore.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * SecurityContext 에서 전역으로 유저 정보를 제공하는 유틸 클래스
 */
public class SecurityUtil {

    // JwtFilter 의 doFilterInteranl 메소드에 request 가 들어올 떄,
    // Security Context 에 Authentication 객체가 저장된다.
    // SecurityContext 는 ThreadLocal 에 사용자의 정보를 저장한다.
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }

        return Long.parseLong(authentication.getName());
    }

}
