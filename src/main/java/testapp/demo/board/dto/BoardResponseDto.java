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
//    private SubCategoryResponseDTO subCategory;
    private long subCategoryId;
    private String content;
    private Long creator;
    private LocalDateTime regDt;


    public BoardResponseDto() {
    }


    public BoardResponseDto fromEntity(Board board){
        return BoardResponseDto.builder()
                .id(board.getId())
                .subCategoryId(board.getSubCategoryId())
                .title(board.getTitle())
                .content(board.getContent())
                .creator(board.getCreator())
                .regDt(board.getRegDt()).build();
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
