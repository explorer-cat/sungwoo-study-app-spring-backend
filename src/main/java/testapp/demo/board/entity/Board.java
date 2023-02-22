package testapp.demo.board.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor    //첫번째 방법
@Entity
public class Board {

    @Id
    @GeneratedValue
    @Column(name="board_ID")
    private Long id;
    @Column(name ="main_category_ID")
    private Long mainCategoryId;
    @Column(name="sub_category_ID")
    private Long subCategoryId;
    @Column(name ="member_ID")
    private Long memberId;
    private String title;
    private String content;
    private boolean approval;
    private Long creator;
    private LocalDateTime regDt;


//    @Builder
//    public Board(int id, int categoryId, String title, String content, boolean approval, Long creator, LocalDateTime regDt) {
//        this.id = id;
//        this.categoryId = categoryId;
//        this.title = title;
//        this.content = content;
//        this.approval = approval;
//        this.creator = creator;
//        this.regDt = regDt;
//    }
}
