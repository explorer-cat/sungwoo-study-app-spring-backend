package testapp.demo.board.service;

import org.springframework.http.ResponseEntity;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.dto.CreatePostRequest;
import testapp.demo.board.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
//    ResponseEntity<BoardResponseDto> createPost(Map<String, Object> data);

    ResponseEntity<Board> setPost(CreatePostRequest data);
    Optional<Board> getPost(long subCategoryId, long postId);

    List<Board> getAllPost(long subCategoryId);


//    ResponseEntity<List<BoardResponseDto>> getCategoryPostList(int categoryId);

}
