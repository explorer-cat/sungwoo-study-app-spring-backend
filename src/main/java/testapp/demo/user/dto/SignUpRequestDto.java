package testapp.demo.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import testapp.demo.user.entity.UserVo;

@Data
@NoArgsConstructor
@ToString
public class SignUpRequestDto {
    private String uid;
    private String email;
    private String nickname;
    private String profileImage;


    public SignUpRequestDto(String uid, String email, String nickname, String profileImage) {
        this.uid = uid;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
