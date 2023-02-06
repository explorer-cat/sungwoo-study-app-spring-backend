package testapp.demo.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.entity.BoardVo;
import testapp.demo.board.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;


    /**
     * @param data
     * @return
     * @title: 게시글 작성
     */
//    @Override
//    public ResponseEntity<BoardVo> createPost(Map<String, Object> data) {
//        BoardVo boardVo = new BoardVo();
//        boardVo.setCategoryId(2);
//        boardVo.setTitle("IOC의 대해서 설명하세요.");
//        boardVo.setContent("뭔데");
//        boardVo.setCreator("최성우");
//        boardVo.setRegDt(LocalDateTime.now());
//        boardRepository.save(boardVo);
//        return new ResponseEntity<>(boardRepository.save(boardVo), HttpStatus.OK);
//    }


    /**
     * @Title : 카테고리 단건 게시글 조회.
     * @param id
     * @return List<Board>
     */
    @Override
    public ResponseEntity<List<BoardResponseDto>> getPost(int id) {
        List<BoardResponseDto> boardList = new ArrayList<>();
        BoardVo board = boardRepository.findById(id).get();
        boardList.add(new BoardResponseDto(board));
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    /**
     * @Title : 카테고리 모든 게시글 조회
     * @param categoryId
     * @return
     * save get update  return
     */
    @Override
    public ResponseEntity<List<BoardResponseDto>> getCategoryPostList(int categoryId) {
        try {
            return new ResponseEntity<>(boardRepository.findByCategoryIdAndApproval(categoryId,true), HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
