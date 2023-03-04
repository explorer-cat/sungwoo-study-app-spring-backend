package testapp.demo.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.dto.CreatePostRequest;
import testapp.demo.board.entity.Board;
import testapp.demo.board.service.BoardService;

import java.net.InetAddress;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardControllerV1 {
    private BoardService boardService;

    @Autowired
    public BoardControllerV1(BoardService boardService) {
        this.boardService = boardService;
    }



    /**
     * @title 해당 전체 카테고리 게시글 모두 조회
     * @param subCategoryId
     * @return
     */
    @GetMapping("/{subCategoryId}")
    public ResponseEntity<List<BoardResponseDto>> getAllPostList(@PathVariable("subCategoryId") long subCategoryId) {
       try {

           BoardResponseDto dto = new BoardResponseDto();
           List<Board> allPost = boardService.getAllPost(subCategoryId);

           System.out.println("allPost = " + allPost);

           List result = new ArrayList();

           for (Board v : allPost) {
               result.add(dto.fromEntity(v));
           }

           return new ResponseEntity<>(result,HttpStatus.OK);
       } catch (Exception e) {
           System.err.println(e);
           return ResponseEntity.internalServerError().build();
       }
    }

    /**
     * @title 특정 게시물 단건 조회
     * @param subCategoryId
     * @param postId
     * @return
     */
    @GetMapping("/{subCategoryId}/{postId}")
    public ResponseEntity<BoardResponseDto> getPost(@PathVariable("subCategoryId") long subCategoryId, @PathVariable("postId") long postId) {
        try {
            BoardResponseDto dto = new BoardResponseDto();
            Optional<Board> post = boardService.getPost(subCategoryId, postId);
            return new ResponseEntity<>(dto.fromEntity(post.get()), HttpStatus.OK);
        } catch (IllegalStateException e) {
            if(e.getMessage().equals("empty")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{mainCategoryId}/{subCategoryId}")
    public ResponseEntity createPost(@PathVariable("mainCategoryId") long mainCategoryId,
                                     @PathVariable("subCategoryId") long subCagtegoryId,
                                     @RequestBody CreatePostRequest request) {
        try {
            boardService.createPost(mainCategoryId, subCagtegoryId, request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
