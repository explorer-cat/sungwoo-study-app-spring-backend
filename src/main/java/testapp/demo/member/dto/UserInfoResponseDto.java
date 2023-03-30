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
    private long q_drop_token_points;

    public UserInfoResponseDto(Member member) {
        this.email = member.getEmail();
        this.admin_level = member.getLevel();
        this.nickname = member.getNickname();
        this.thumbnailImage = member.getThumbnailImage();
        this.q_drop_token_points = member.getQ_drop_token_points();
    }
}
