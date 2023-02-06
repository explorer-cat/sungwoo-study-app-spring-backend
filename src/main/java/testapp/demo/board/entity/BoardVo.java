package testapp.demo.board.entity;

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
    private String creator;
    private LocalDateTime regDt;
}
