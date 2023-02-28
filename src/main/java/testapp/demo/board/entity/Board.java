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
    @JoinColumn(name = "main_category_ID",insertable=false, updatable=false)
    private MainCategory mainCategory;
    @Column(name="main_category_ID")
    private Long mainCategoryId;

    @ManyToOne(targetEntity = SubCategory.class)
    @JoinColumn(name = "sub_category_ID", insertable=false, updatable=false)
    private SubCategory subCategory;

    @Column(name="sub_category_ID")
    private Long subCategoryId;

//    @OneToMany
//    @JoinColumn("name")
//    private Member member;
    @Column(name ="member_ID")
    private long memberId;
    private String title;
    private String content;
    private boolean approval;
    private Long creator;
    private LocalDateTime regDt;

    public Board(long id, MainCategory mainCategory, Long mainCategoryId, SubCategory subCategory, Long subCategoryId, long memberId, String title, String content, boolean approval, Long creator, LocalDateTime regDt) {
        this.id = id;
        this.mainCategory = mainCategory;
        this.mainCategoryId = mainCategoryId;
        this.subCategory = subCategory;
        this.subCategoryId = subCategoryId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.approval = approval;
        this.creator = creator;
        this.regDt = regDt;
    }

    public MainCategoryResponseDTO getMainCategory() {
        MainCategoryResponseDTO dto = new MainCategoryResponseDTO();
        return null;//dto.fromEntity(mainCategory);
    }

    public SubCategoryResponseDTO getSubCategory() {
        SubCategoryResponseDTO dto = new SubCategoryResponseDTO();
        return null;
//        return dto.fromEntity(subCategory);
    }
}
