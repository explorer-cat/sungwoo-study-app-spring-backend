package testapp.demo.board.dto;

import lombok.*;
import testapp.demo.board.entity.Board;
import testapp.demo.board.entity.BoardLike;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@ToString
@AllArgsConstructor

public class BoardResponseDto {
    private long id;
    private String title;
    private String content;
    private boolean isAuthor; //해당 게시글의 작성자 인가?
    private int approval; //tmdd
    private Map<String, Object> mainCategory;
    private Map<String, Object> subCategory;
    private Map<String, Object> board_like;
    private Map<String, Object> member_info;
    private Map<String, Object> bookmark_info;
    private LocalDateTime createTime;


    public BoardResponseDto() {
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
