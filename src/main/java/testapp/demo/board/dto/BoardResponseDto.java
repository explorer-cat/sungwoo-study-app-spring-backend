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
    private Map<String,Object> mainCategory;
    private Map<String,Object> subCategory;
    private Map<String, Object> board_like;
    private Long creator;
    private LocalDateTime createTime;


    public BoardResponseDto() {
    }






    //로그인 회원일 경우
    public BoardResponseDto fromEntity(Board board,String userEmail) {
        Map<String, Object> board_count = new HashMap<>();
        //해당 게시글 좋아요 갯수 추가.
        board_count.put("like_count",board.getBoardLike().size());
        board_count.put("user_like_status", false);

        //사용자 이메일이 null이 아닐 경우 : 로그인이 정상적으로 확인된 사용자임
        //해당 게시글을 좋아요 하고있는지 확인하는 로직을 거침.
        if(userEmail != null) {
            List<BoardLike> boardLike = board.getBoardLike();

            for (BoardLike like : boardLike) {
                System.out.println("like.getMember() = " + like.getMember().getEmail());
                if (like.getMember().getEmail() == userEmail) {
                    board_count.replace("user_like_status",true);
                    break;
                }
            }
        }
        //메인 카테고리 세팅
        Map<String, Object> mainCategory = new HashMap<>();
        mainCategory.put("main_category_id",board.getMainCategory().getId());
        mainCategory.put("main_category_name",board.getMainCategory().getName());

        //서브 카테고리 세팅
        Map<String, Object> subCategory = new HashMap<>();
        subCategory.put("sub_category_id",board.getSubCategory().getId());
        subCategory.put("sub_category_name", board.getSubCategory().getName());


        return BoardResponseDto.builder()
                .id(board.getId())
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .board_like(board_count)
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
