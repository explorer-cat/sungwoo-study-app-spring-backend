package testapp.demo.board.dto;
import lombok.*;
import testapp.demo.board.entity.Board;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor

public class BoardResponseDto {
    private long id;
    private String title;
    private long mainCategory_id;
    private String mainCategory_name;
    private long subCategory_id;
    private String subCategory_name;
    private String content;
    private Long creator;
    private LocalDateTime createTime;


    public BoardResponseDto() {
    }


    public BoardResponseDto fromEntity(Board board){
       return BoardResponseDto.builder()
                .id(board.getId())
                .mainCategory_id(board.getMainCategory().getId())
                .mainCategory_name(board.getMainCategory().getName())
                .subCategory_id(board.getSubCategory().getId())
                .subCategory_name(board.getSubCategory().getName())
                .title(board.getTitle())
                .content(board.getContent())
               .createTime(board.getCreateTime()).build();
    }


//    public BoardResponseDto(Board board) {
//        this.id = board.getId();
//        this.categoryId = board.getCategoryId();
//        this.title = board.getTitle();
//        this.content = board.getContent();
//        this.creator = board.getCreator();
//        this.regDt = board.getRegDt();
//    }
}
