package testapp.demo.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.board.entity.Board;
import testapp.demo.board.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;


    /**
     * @param data
     * @return
     * @title: 게시글 작성
     */
    @Override
    public ResponseEntity<Board> createPost(Map<String, Object> data) {
        Board board = new Board();
        board.setCategoryId(2);
        board.setTitle("IOC의 대해서 설명하세요.");
        board.setContent("뭔데");
        board.setCreator("최성우");
        board.setRegDt(LocalDateTime.now());
        boardRepository.save(board);
        return new ResponseEntity<>(boardRepository.save(board), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<List<Board>> getPost(int id) {
        //boardRepository.findById(id) optinal -> List
        List<Board> board = new ArrayList<Board>();
        Board board1 = boardRepository.findById(id).get();
        board.add(board1);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Board>> getCategoryPostList(int categoryId) {
        try {
            return new ResponseEntity<>(boardRepository.findByCategoryId(categoryId), HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
