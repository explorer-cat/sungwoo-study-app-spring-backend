package testapp.demo.board.entity;

import jdk.nashorn.internal.runtime.Debug;
import lombok.AllArgsConstructor;
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
public class BoardVo {

    @Id
    @GeneratedValue
    private int id;
    private int categoryId;
    private String title;
    private String content;
    private boolean approval;
    private Long creator;
    private LocalDateTime regDt;


    @Builder
    public BoardVo(int id, int categoryId, String title, String content, boolean approval, Long creator, LocalDateTime regDt) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.approval = approval;
        this.creator = creator;
        this.regDt = regDt;
    }
}
