package testapp.demo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testapp.demo.board.entity.Board;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BoardRepository extends JpaRepository<Board,Integer> {
    List<Board> findByCategoryId(int id);

//    ArrayList<Board> findByCategoryIdAndId(int categoryId, int id);


}
