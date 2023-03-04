package testapp.demo.member.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@ToString
@Getter
public class LoginTokenResponseDto {
    private int code;
    private JSONObject data;
    private TokenResponse token;

    public LoginTokenResponseDto(int code, JSONObject data, TokenResponse token) {
        this.code = code;
        this.data = data;
        this.token = token;
    }
}


