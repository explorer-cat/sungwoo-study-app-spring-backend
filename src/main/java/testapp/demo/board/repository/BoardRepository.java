package testapp.demo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Integer> {
    List<Board> findByCategoryId(int id);

    List<BoardResponseDto> findByCategoryIdAndApproval(int id, boolean approval);



//    ArrayList<Board> findByCategoryIdAndId(int categoryId, int id);


}
