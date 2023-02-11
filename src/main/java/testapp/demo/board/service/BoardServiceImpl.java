package testapp.demo.board.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.dto.CreatePostRequest;
import testapp.demo.board.entity.BoardVo;
import testapp.demo.board.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private Logger log = LoggerFactory.getLogger(getClass());

    private final BoardRepository boardRepository;


    /**
     * @param data
     * @return
     * @title: 게시글 작성
     */
    @Override
    public ResponseEntity<BoardVo> setPost(CreatePostRequest data) {
        log.info("BoardServiceImpl class : setPost() start");

        BoardVo boardVo = new BoardVo();

        boardVo.setCategoryId(data.getCategoryId());
        boardVo.setTitle(data.getTitle());
        boardVo.setContent(data.getContent());
        boardVo.setCreator(data.getCreator());
        boardVo.setRegDt(LocalDateTime.now());

        boardRepository.save(boardVo);
        return new ResponseEntity<>(boardRepository.save(boardVo), HttpStatus.OK);
    }


    /**
     * @Title : 카테고리 단건 게시글 조회.
     * @param id
     * @return List<Board>
     */
    @Override
    public ResponseEntity<List<BoardResponseDto>> getPost(int id) {
        log.info("BoardServiceImpl class : getPost() start");

        List<BoardResponseDto> boardList = new ArrayList<>();
        Optional<BoardVo> board = boardRepository.findById(id);

        //게시글을 못 찾을 경우
        if(!board.isPresent()) {
            throw new IllegalArgumentException();
        } else {
            boardList.add(new BoardResponseDto(board.get()));
        }

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
            log.info("BoardServiceImpl class : getCategoryPostList() start");

            return new ResponseEntity<>(boardRepository.findByCategoryIdAndApproval(categoryId,true), HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
