package testapp.demo.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import testapp.demo.member.entity.Member;

@Data
@NoArgsConstructor
@ToString
public class UserInfoResponseDto {
    private int id;
    private String uid;
    private String email;
    private String nickname;
    private String thumbnailImage;
    private boolean admin;

    public UserInfoResponseDto(Member member) {
        this.id = member.getId();
        this.uid = member.getUid();
        this.email = member.getEmail();
        this.admin = member.isAdmin();
        this.nickname = member.getNickname();
        this.thumbnailImage = member.getThumbnailImage();
    }
}
