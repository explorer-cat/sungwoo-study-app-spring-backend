package testapp.demo.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testapp.demo.board.entity.Board;
import testapp.demo.board.repository.BoardRepository;
import testapp.demo.board.service.BoardService;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * @param categoryId: 카테고리 아이디
     * @return
     * @title 해당 카테고리의 작성된 게시글들을 가져옴.
     */
    @GetMapping("/api/v1/board")
    //@RequestParam(value = "category",required = false, defaultValue = "0") category가 없어도 들어오게
    public ResponseEntity<?> getCategoryPostInfo(
            @RequestParam(value = "category", required = false , defaultValue = "0") int categoryId,
            @RequestParam(value = "postId", required = false, defaultValue = "0") int postId) {


        /**
         * case 1 : 카테고리와 게시글번호가 둘다 없는 경우 요청자체가 잘못됨.
         * case 2 : 카테고리 아이디만 요청했을 경우 -> 해당 카테고리 모든 게시글 정보 반환
         * case 3 : 카테고리 아이디와 게시글 아이디를 요청했을 경우 -> 해당 카테고리에 있는 게시글 정보만 반환
         */

        if (categoryId == 0) { //카테고리와 게시글번호가 둘다 없는 경우
            System.out.println("category prams required");
        } else if(categoryId != 0 && postId == 0) { //카테고리 아이디만 왔을 경우 해당 카테고리 모든 게시글 정보 반환
            return boardService.getCategoryPostList(categoryId);
        } else if(categoryId != 0 && postId != 0) {
            return boardService.getPost(postId);
        }
        return null;
    }

//    /**
//     * @title:특정 게시글 내용을 가져옵니다.
//     * @param id
//     * @return
//     */
//    @GetMapping("/api/v1/board/content")
//    //@RequestParam(value = "category",required = false, defaultValue = "0") category가 없어도 들어오게
//    public ResponseEntity<Board> getBoardContent(@RequestParam(value = "id") long id) {
//        return boardService.getBoardContent(id);
//    }


//    @PostMapping("/api/v1/board")
//    @ResponseBody
//    public ResponseEntity<Board> createBoard(@RequestBody Map<String, Object> data) {
//        try {
//            return boardService.createBoardItem(data);
////            return new ResponseEntity<>(boardService.getBoardList(data), HttpStatus.OK);
//        } catch (RuntimeException re) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
