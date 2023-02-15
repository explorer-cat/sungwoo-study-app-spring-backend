package testapp.demo.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.json.simple.JSONObject;
import testapp.demo.user.entity.UserVo;

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


