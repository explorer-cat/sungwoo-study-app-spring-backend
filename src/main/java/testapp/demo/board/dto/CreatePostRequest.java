package testapp.demo.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import testapp.demo.board.entity.Board;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor

public class CreatePostRequest {
    private String title;
    private String tag;
    private String content;
    private Long creator;

//    public Board toEntity(){
//        return Board.builder()
//                .categoryId(categoryId)
//                .title(title)
//                .content(content)
//                .creator(creator)
//                .build();
//    }
}
