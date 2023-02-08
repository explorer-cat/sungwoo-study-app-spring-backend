package testapp.demo.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import testapp.demo.board.entity.BoardVo;

@Data
@NoArgsConstructor
@ToString
public class CreatePostRequest {
    private int categoryId;
    private String title;
    private String content;
    private String creator;

    public BoardVo toEntity(){
        return BoardVo.builder()
                .categoryId(categoryId)
                .title(title)
                .content(content)
                .creator(creator)
                .build();
    }
}
