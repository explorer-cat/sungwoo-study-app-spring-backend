package testapp.demo.board.service;

import org.springframework.http.ResponseEntity;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.dto.CreatePostRequest;
import testapp.demo.board.entity.Board;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BoardService {
//    ResponseEntity<BoardResponseDto> createPost(Map<String, Object> data);

    Board createPost(long subCategoryId, CreatePostRequest data) throws Exception;
    BoardResponseDto getPost(long subCategoryId, long postId);

    List<BoardResponseDto> getMyPost() throws Exception;

    List<BoardResponseDto> getMyBookMarkPost() throws Exception;

    List<BoardResponseDto> getAllPost(List<Long> subCategories,String keyword,String sortTarget,String sortType);

    void addPostLike(long postId);

    void cancelPostLike(long postId);


    void addPostBookmark(long postId);
    void cancelPostBookmark(long postId);

//    List<BoardResponseDto> getSearchPostList(String search_keyword);

//    Set<Long> getUserLikesPostList();

//    ResponseEntity<List<BoardResponseDto>> getCategoryPostList(int categoryId);

}
