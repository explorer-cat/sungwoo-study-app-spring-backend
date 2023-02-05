package testapp.demo.board.service;

import org.springframework.http.ResponseEntity;
import testapp.demo.board.entity.Board;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BoardService {
    ResponseEntity<Board> createPost(Map<String, Object> data);

    ResponseEntity<List<Board>> getPost(int id);

    ResponseEntity<List<Board>> getCategoryPostList(int categoryId);
}
