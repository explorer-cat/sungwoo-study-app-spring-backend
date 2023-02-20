package testapp.demo.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
