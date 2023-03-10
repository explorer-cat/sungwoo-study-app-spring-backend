package testapp.demo.member.entity;

import lombok.*;
import testapp.demo.board.entity.BoardLike;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.bookmark.entity.SubCategoryBookMark;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Getter
//@Setter
@Getter
@Setter
@NoArgsConstructor    //첫번째 방법
@Entity
public class Member {

    @Id
    @Column(name = "user_email")
    private String email; //
    private String nickname; //사용자 닉네임
    private String thumbnailImage; //카카오톡 프로필 이미지
    @OneToMany(mappedBy = "member")
    private List<MainCategoryBookMark> mainCategoryBookMark = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<SubCategoryBookMark> subCategoryBookMark = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<BoardLike> boardLikes = new ArrayList<>();
    private String refreshToken; //리프레쉬 토큰
    private boolean ban; //계정 정지 여부
    private byte level; //관리자 여부
    private String social_type;
    private LocalDateTime createDate;

    @Builder
    public Member(String email, String nickname, String thumbnailImage, String refreshToken, boolean ban, byte level, String social_type, LocalDateTime createDate) {
        this.email = email;
        this.nickname = nickname;
        this.thumbnailImage = thumbnailImage;
        this.refreshToken = refreshToken;
        this.ban = ban;
        this.level = level;
        this.social_type = social_type;
        this.createDate = createDate;
    }

}
