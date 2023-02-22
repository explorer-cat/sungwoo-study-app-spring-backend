package testapp.demo.board.dto;
import lombok.*;
import testapp.demo.board.entity.Board;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class BoardResponseDto {
    private int id;
    private int categoryId;
    private String title;
    private String content;
    private Long creator;
    private LocalDateTime regDt;

//    public BoardResponseDto(Board board) {
//        this.id = board.getId();
//        this.categoryId = board.getCategoryId();
//        this.title = board.getTitle();
//        this.content = board.getContent();
//        this.creator = board.getCreator();
//        this.regDt = board.getRegDt();
//    }
}
