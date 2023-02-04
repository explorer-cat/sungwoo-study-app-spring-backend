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
     * @title 해당 카테고리의 작성된 게시글들을 가져옴.
     * @param categoryId: 카테고리 아이디
     * @return
     */
    @GetMapping("/api/v1/board")
    //@RequestParam(value = "category",required = false, defaultValue = "0") category가 없어도 들어오게
    public ResponseEntity<List<Board>> getBoardList(@RequestParam(value = "category",required = false, defaultValue = "0") int categoryId) {
         return boardService.getBoardList(categoryId);
    }

    @GetMapping("/api/v1/board/content")
    //@RequestParam(value = "category",required = false, defaultValue = "0") category가 없어도 들어오게
    public ResponseEntity<Board> getBoardContent(@RequestParam(value = "id") long id) {
        return boardService.getBoardContent(id);
    }



    @PostMapping("/api/v1/board")
    @ResponseBody
    public ResponseEntity<Board> createBoard(@RequestBody Map<String,Object> data) {
        try {
            return boardService.createBoardItem(data);
//            return new ResponseEntity<>(boardService.getBoardList(data), HttpStatus.OK);
        } catch (RuntimeException re) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
