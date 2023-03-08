package testapp.demo.board.entity;

import lombok.*;
import org.jboss.jandex.Main;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor    //첫번째 방법
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

    private String title;
    private String content;
    private boolean approval;
    private LocalDateTime createTime;

    public Board(long id, MainCategory mainCategory, SubCategory subCategory, Member member, List<BoardLike> boardLike, String title, String content, boolean approval, LocalDateTime createTime) {
        this.id = id;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.member = member;
        this.boardLike = boardLike;
        this.title = title;
        this.content = content;
        this.approval = approval;
        this.createTime = createTime;
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
