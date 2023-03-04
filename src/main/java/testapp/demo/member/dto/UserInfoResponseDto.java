package testapp.demo.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import testapp.demo.member.entity.Member;

@Data
@NoArgsConstructor
@ToString
public class UserInfoResponseDto {
    private String email;
    private String nickname;
    private String thumbnailImage;
    private byte admin_level;

    public UserInfoResponseDto(Member member) {
        this.email = member.getEmail();
        this.admin_level = member.getLevel();
        this.nickname = member.getNickname();
        this.thumbnailImage = member.getThumbnailImage();
    }
}
