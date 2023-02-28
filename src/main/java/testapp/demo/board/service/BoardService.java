package testapp.demo.board.service;

import org.springframework.http.ResponseEntity;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.dto.CreatePostRequest;
import testapp.demo.board.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
//    ResponseEntity<BoardResponseDto> createPost(Map<String, Object> data);

    Board createPost(long mainCategoryId, long subCategoryId, CreatePostRequest data) throws Exception;
    Optional<Board> getPost(long subCategoryId, long postId);

    List<Board> getAllPost(long subCategoryId);


//    ResponseEntity<List<BoardResponseDto>> getCategoryPostList(int categoryId);

}
