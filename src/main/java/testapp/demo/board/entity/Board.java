package testapp.demo.board.entity;

import lombok.*;
import org.jboss.jandex.Main;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor    //첫번째 방법
@AllArgsConstructor
@Entity
@Builder
public class Board {

    @Id
    @GeneratedValue
    @Column(name="board_ID")
    private long id;
    @ManyToOne(targetEntity = MainCategory.class)
    @JoinColumn(name = "main_category_ID")
    private MainCategory mainCategory;

    @ManyToOne(targetEntity = SubCategory.class)
    @JoinColumn(name = "sub_category_ID")
    private SubCategory subCategory;

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "user_email")
    private Member member;


    //해당 게시글 좋아요 개수 조회를 위한 조인 컬럼.
    @OneToMany(mappedBy = "board")
    private List<BoardLike> boardLike;

    @OneToMany(mappedBy = "board")
    private List<BoardBookmark> boardBookmarks;

    private String title;
    private String content;
    private boolean isRemove;
    private int approval;
    private LocalDateTime createTime;


    public Board toEntity(Board board) {
        Board build = Board.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .member(board.getMember())
                .boardLike(board.getBoardLike())
                .boardBookmarks(board.getBoardBookmarks())
                .build();
        System.out.println("build = " + build);
        return null;
//        return Board.
//
//                builder().build();
    }

    public Map<String, Object> setUserInfo(Board board) {
        Map<String, Object> memberInfo = new HashMap<>();
        memberInfo.put("nickname", board.getMember().getNickname());
        memberInfo.put("profile_img", board.getMember().getThumbnailImage());
        return memberInfo;
    }

    public Map<String,Object> getMainCategoryInfo(Board board) {
        Map<String, Object> main_category_info = new HashMap<>();

        main_category_info.put("main_category_id", board.getMainCategory().getId());
        main_category_info.put("main_category_name", board.getMainCategory().getName());

        return main_category_info;
    }


    public Map<String,Object> getSubCategoryInfo(Board board) {
        Map<String, Object> sub_category_info = new HashMap<>();

        sub_category_info.put("sub_category_id", board.getSubCategory().getId());
        sub_category_info.put("sub_category_name", board.getSubCategory().getName());

        return sub_category_info;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", mainCategory=" + mainCategory +
                ", subCategory=" + subCategory +
                ", member=" + member +
                ", boardLike=" + boardLike +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", approval=" + approval +
                ", createTime=" + createTime +
                '}';
    }


    //    public MainCategoryResponseDTO getMainCategory() {
//        MainCategoryResponseDTO dto = new MainCategoryResponseDTO();
//        return dto.fromEntity(mainCategory);
//    }

//    public SubCategoryResponseDTO getSubCategory() {
//        SubCategoryResponseDTO dto = new SubCategoryResponseDTO();
//        return null;
////        return dto.fromEntity(subCategory);
//    }
}
