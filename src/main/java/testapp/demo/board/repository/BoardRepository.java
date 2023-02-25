package testapp.demo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Integer> {
//    List<Board> findByCategoryId(int id);
//
    List<Board> findBySubCategoryId(long subCategoryId);

    Optional<Board> findBySubCategoryIdAndId(long subCategoryId, long postId);
//


//    ArrayList<Board> findByCategoryIdAndId(int categoryId, int id);


}
