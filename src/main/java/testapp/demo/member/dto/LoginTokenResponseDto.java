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
    private String rtoken;
    private String atoken;

    public LoginTokenResponseDto(int code, JSONObject data, String rtoken,String atoken) {
        this.code = code;
        this.data = data;
        this.rtoken = rtoken;
        this.atoken = atoken;
    }
}


