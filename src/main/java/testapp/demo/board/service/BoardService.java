package testapp.demo.board.service;

import org.springframework.http.ResponseEntity;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.entity.BoardVo;

import java.util.List;
import java.util.Map;

public interface BoardService {
//    ResponseEntity<BoardResponseDto> createPost(Map<String, Object> data);

    ResponseEntity<List<BoardResponseDto>> getPost(int id);

    ResponseEntity<List<BoardResponseDto>> getCategoryPostList(int categoryId);
}
