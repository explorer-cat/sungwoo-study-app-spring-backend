package testapp.demo.board.dto;
import lombok.*;
import testapp.demo.board.entity.BoardVo;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class BoardResponseDto {
    private int id;
    private int categoryId;
    private String title;
    private String content;
    private String creator;
    private LocalDateTime regDt;

    public BoardResponseDto(BoardVo boardVo) {
        this.id = boardVo.getId();
        this.categoryId = boardVo.getCategoryId();
        this.title = boardVo.getTitle();
        this.content = boardVo.getContent();
        this.creator = boardVo.getCreator();
        this.regDt = boardVo.getRegDt();
    }
}
