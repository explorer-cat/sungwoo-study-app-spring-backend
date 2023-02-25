package testapp.demo.board.entity;

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
public class PostTag {

    @Id
    @GeneratedValue
    @Column(name="tag_ID")
    private long id;
    @Column(name ="post_ID")
    private long postId;
    private String tagName;



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
