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

    @GetMapping("/{subCategoryId}")
    public ResponseEntity<List<Board>> getAllPostList(@PathVariable("subCategoryId") long subCategoryId) {
       try {
           return new ResponseEntity<>(boardService.getAllPost(subCategoryId), HttpStatus.OK);
       } catch (Exception e) {
           System.err.println(e);
           return ResponseEntity.internalServerError().build();
       }
    }

    @GetMapping("/{subCategoryId}/{postId}")
    public ResponseEntity<Board> getPost(@PathVariable("subCategoryId") long subCategoryId, @PathVariable("postId") long postId) {
        try {
            Optional<Board> post = boardService.getPost(subCategoryId, postId);
            return new ResponseEntity<>(post.get(), HttpStatus.OK);
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
}
