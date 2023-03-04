package testapp.demo.board.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import testapp.demo.board.dto.CreatePostRequest;
import testapp.demo.board.entity.Board;
import testapp.demo.board.repository.BoardRepository;
import testapp.demo.category.repository.MainCategoryRepository;
import testapp.demo.category.repository.SubCategoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private Logger log = LoggerFactory.getLogger(getClass());

    private final BoardRepository boardRepository;
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;


    /**
     * @param data
     * @return
     * @title: 게시글 작성
     */
    @Override
    public Board createPost(long mainCategoryId, long subCategoryId, CreatePostRequest data) throws Exception {
        try {
            Board board = Board.builder()
                    .title(data.getTitle())
                    .content(data.getContent())
                    .approval(true)
                    .createTime(LocalDateTime.now()).build();

            board.setMainCategory(mainCategoryRepository.findById(mainCategoryId).get());
            board.setSubCategory(subCategoryRepository.findById(subCategoryId).get());

            System.out.println("board = " + board);

            Board save = boardRepository.save(board);
//            Board save = null;
            return save;
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }



    /**
     * @return List<Board>
     * @Title : 카테고리 단건 게시글 조회.
     */
    @Override
    public Optional<Board> getPost(long subCategoryId, long postId) {
        Optional<Board> board = boardRepository.findBySubCategoryIdAndId(subCategoryId,postId);

        //게시글을 못 찾을 경우
        if (!board.isPresent()) {
            throw new IllegalArgumentException("empty");
        } else {
            return board;
        }
    }


    @Override
    public List<Board> getAllPost(long subCategoryId) {
        return boardRepository.findBySubCategoryId(subCategoryId);
    }
}

