package testapp.demo.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse<T> {

    private String code;
    private String msg;
    private T data;
}