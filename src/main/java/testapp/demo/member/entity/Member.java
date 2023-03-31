package testapp.demo.member.entity;

import lombok.*;
import testapp.demo.board.entity.Board;
import testapp.demo.board.entity.BoardBookmark;
import testapp.demo.board.entity.BoardLike;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.bookmark.entity.SubCategoryBookMark;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Getter
//@Setter
@Getter
@Setter
@NoArgsConstructor    //첫번째 방법
@Entity
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @Column(name = "user_email")
    private String email; //
    private String nickname; //사용자 닉네임
    private String thumbnailImage; //카카오톡 프로필 이미지
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<MainCategoryBookMark> mainCategoryBookMark = new ArrayList<>();
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<SubCategoryBookMark> subCategoryBookMark = new ArrayList<>();
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<BoardLike> boardLikes = new ArrayList<>();
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<BoardBookmark> boardBookmarks = new ArrayList<>();
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();
    private long q_drop_token_points = 100;
    private String refreshToken; //리프레쉬 토큰
    private boolean ban; //계정 정지 여부
    private byte level; //관리자 여부
    private String social_type;
    private boolean isWithdrawal; //탈퇴 여부
    private String withdrawal_reason; //탈퇴 사유
    private LocalDateTime withdrawal_date; //탈퇴일
    private LocalDateTime createDate;


    public void addQDropTokenPoint(long point) {
        this.q_drop_token_points += point;
    }

    public void removeQDropTokenPoint(long point) {
        this.q_drop_token_points -= point;
    }
}
