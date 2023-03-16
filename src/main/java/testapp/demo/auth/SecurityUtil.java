package testapp.demo.auth;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getUserEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }
        System.out.println("authentication = " + authentication.getName().equals("sqlstyle@kakao.com"));
        System.out.println("authentication = sqlstyle@kakao.com");
//        return "sqlstyle@kakao.com";
        return authentication.getName();
    }
}
