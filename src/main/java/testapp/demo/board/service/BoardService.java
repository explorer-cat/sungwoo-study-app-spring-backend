package testapp.demo.board.service;

import org.springframework.http.ResponseEntity;
import testapp.demo.board.entity.Board;

import java.util.List;
import java.util.Map;

public interface BoardService {
    ResponseEntity<Board> createBoardItem(Map<String, Object> data);

    ResponseEntity<List<Board>> getBoardList(int categoryId);

}
