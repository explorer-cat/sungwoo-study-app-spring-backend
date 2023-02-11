package testapp.demo.user.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor    //첫번째 방법
@Entity
public class UserVo {

    @Id
    @GeneratedValue
    private int id;
    private String email;
    private String nickname;
    private String password;
    private String thumbnailImage;
    private boolean ban;
    private boolean admin;
    private LocalDateTime regDt;

    @Builder
    public UserVo(int id, String email, String nickname, String password, String thumbnailImage, boolean ban, boolean admin, LocalDateTime regDt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.thumbnailImage = thumbnailImage;
        this.ban = ban;
        this.admin = admin;
        this.regDt = regDt;
    }
}
