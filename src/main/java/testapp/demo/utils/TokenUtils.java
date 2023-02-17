package testapp.demo.utils;


import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Component
public class TokenUtils {
    public String myUtilMethod(String str) {
        // 문자열을 변환하는 간단한 유틸리티 메서드
        return str.toUpperCase();
    }
}
