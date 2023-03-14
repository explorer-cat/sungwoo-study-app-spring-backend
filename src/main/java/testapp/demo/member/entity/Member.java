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
    @OneToMany(mappedBy = "member")
    private List<MainCategoryBookMark> mainCategoryBookMark = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<SubCategoryBookMark> subCategoryBookMark = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<BoardLike> boardLikes = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<BoardBookmark> boardBookmarks = new ArrayList<>();
    private String refreshToken; //리프레쉬 토큰
    private boolean ban; //계정 정지 여부
    private byte level; //관리자 여부
    private String social_type;
    private LocalDateTime createDate;



}
