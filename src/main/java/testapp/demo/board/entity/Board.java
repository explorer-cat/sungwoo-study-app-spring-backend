package testapp.demo.board.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Board {

    @Id
    @GeneratedValue
    private int id;
    private int categoryId;
    private String title;
    private String content;
    private String creator;
    private LocalDateTime regDt;
}
