package testapp.demo.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseNoData<T> {

    private String code;
    private String msg;
    private String userEmail;
}