package testapp.demo.board.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.jandex.Main;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
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
    private String title;
    private String content;
    private boolean approval;
    private LocalDateTime createTime;

    public Board(long id, MainCategory mainCategory, SubCategory subCategory, Member member, String title, String content, boolean approval, LocalDateTime createTime) {
        this.id = id;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.member = member;
        this.title = title;
        this.content = content;
        this.approval = approval;
        this.createTime = createTime;
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
