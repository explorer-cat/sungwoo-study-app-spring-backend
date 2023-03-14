package testapp.demo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findBySubCategoryId(long subCategoryId);

    Board findBySubCategoryIdAndId(long subCategoryId, long postId);

    List<Board> findByContentContains(String keyword);


}
