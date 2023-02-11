package testapp.demo.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import testapp.demo.user.entity.UserVo;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class UserInfoResponseDto {
    private int id;
    private String email;
    private String nickname;
    private String thumbnailImage;
    private boolean admin;

    public UserInfoResponseDto(UserVo userVo) {
        this.id = userVo.getId();
        this.email = userVo.getEmail();
        this.admin = userVo.isAdmin();
        this.nickname = userVo.getNickname();
        this.thumbnailImage = userVo.getThumbnailImage();
    }
}
