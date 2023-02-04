package testapp.demo.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.board.entity.Board;
import testapp.demo.board.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;


    /**
     * @title: 게시글 작성
     * @param data
     * @return
     */
    @Override
    public ResponseEntity<Board> createBoardItem(Map<String,Object> data) {
        Board board = new Board();
        board.setCategoryId(2);
        board.setTitle("IOC의 대해서 설명하세요.");
        board.setContent("뭔데");
        board.setCreator("최성우");
        board.setRegDt(LocalDateTime.now());
        boardRepository.save(board);
        return new ResponseEntity<>(boardRepository.save(board), HttpStatus.OK);

    }

    public ResponseEntity<List<Board>> getBoardList(int categoryId) {
        try{
            if(categoryId == 0) {
                return ResponseEntity.ok().body(boardRepository.findAll());
            } else {
                List<Board> board = boardRepository.findByCategoryId(categoryId);
                if(board.size() > 0) {
                    return new ResponseEntity<>(board,HttpStatus.OK);
                } else {
                    //요청한 카테고리에 게시글이 한개도 없을 경우
                    return new ResponseEntity<>(board, HttpStatus.OK);
                }
           }
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
