package testapp.demo.board.service;

import org.springframework.http.ResponseEntity;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.dto.CreatePostRequest;
import testapp.demo.board.entity.Board;

import java.util.List;

public interface BoardService {
//    ResponseEntity<BoardResponseDto> createPost(Map<String, Object> data);

    ResponseEntity<Board> setPost(CreatePostRequest data);

    ResponseEntity<List<BoardResponseDto>> getPost(int id);

    ResponseEntity<List<BoardResponseDto>> getCategoryPostList(int categoryId);

}
