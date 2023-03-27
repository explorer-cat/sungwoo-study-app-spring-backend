package testapp.demo.board.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testapp.demo.auth.SecurityUtil;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.dto.CreatePostRequest;
import testapp.demo.board.entity.Board;
import testapp.demo.board.service.BoardService;
import testapp.demo.member.dto.TokenResponseNoData;
import testapp.demo.member.service.MemberServiceImpl;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin(origins = "*", exposedHeaders = {"Content-Disposition"}, allowedHeaders = "*")
public class BoardControllerV1 {
    @Autowired
    private BoardService boardService;


    /**
     * @param subCategoryId
     * @return
     * @title 해당 전체 카테고리 게시글 모두 조회
     */
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllPostList(
            @RequestParam("sub_id") List<Long> subCategories,
            @RequestParam(name = "search",defaultValue = "" ,required = false) String keyword,
            @RequestParam(name ="sortTarget", defaultValue = "createtime", required = false) String sortTarget,
            @RequestParam(name ="sortType", defaultValue = "desc", required = false) String sortType,
            @RequestParam(name ="paging_num", defaultValue = "0", required = false) int paging_num,
            @RequestParam(name ="paging_count", defaultValue = "1000", required = false) int paging_count) {
        try {
            List<BoardResponseDto> allPost = boardService.getAllPost(subCategories,keyword,sortTarget,sortType,paging_num,paging_count);
            //게시글이 하나도 없는 경우:
            if (allPost == null) {
                return ResponseEntity.notFound().build();
            }
            //로그인 되어있는 사용자라면 게시글의 좋아요 여부를 체크해야하기 때문에 아래 DTO 반환
            return new ResponseEntity<>(allPost, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * @param subCategoryId
     * @param postId
     * @return
     * @title 특정 게시물 단건 조회
     */
    @GetMapping("/{subCategoryId}/{postId}")
    public ResponseEntity<BoardResponseDto> getPost(@PathVariable("subCategoryId") long subCategoryId, @PathVariable("postId") long postId) {
        try {
            return new ResponseEntity<>(boardService.getPost(subCategoryId, postId), HttpStatus.OK);
        } catch (IllegalStateException e) {
            if (e.getMessage().equals("empty")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    //내가 작성한 게시글 보기
    @GetMapping("/my")
    public ResponseEntity<List<BoardResponseDto>> getMyPost(
            @RequestParam(name ="sortType", defaultValue = "desc", required = false) String sortType,
            @RequestParam(name ="paging_num", defaultValue = "0", required = false) int paging_num,
            @RequestParam(name ="paging_count", defaultValue = "1000", required = false) int paging_count) throws Exception {
        try{
            return new ResponseEntity<>(boardService.getMyPost(sortType, paging_num, paging_count), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/bookmark/my")
    public ResponseEntity<List<BoardResponseDto>> getMyBookMarkPost(
            @RequestParam(name ="sortType", defaultValue = "desc", required = false) String sortType,
            @RequestParam(name ="paging_num", defaultValue = "0", required = false) int paging_num,
            @RequestParam(name ="paging_count", defaultValue = "1000", required = false) int paging_count) throws Exception {
        try{
            return new ResponseEntity<>(boardService.getMyBookMarkPost(sortType, paging_num, paging_count), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @PostMapping("{subCategoryId}")
    public ResponseEntity createPost(@PathVariable("subCategoryId") long subCagtegoryId,
                                     @RequestBody CreatePostRequest request) {
        try {
            boardService.createPost(subCagtegoryId, request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity addPostLike(@PathVariable("postId") long postId) {
        boardService.addPostLike(postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/like/{postId}")
    public ResponseEntity cacelPostLike(@PathVariable("postId") long postId) {
        boardService.cancelPostLike(postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bookmark/{postId}")
    public ResponseEntity addPostBookmark(@PathVariable("postId") long postId) {
        boardService.addPostBookmark(postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/bookmark/{postId}")
    public ResponseEntity cancelPostBookmark(@PathVariable("postId") long postId) {
        boardService.cancelPostBookmark(postId);
        return ResponseEntity.ok().build();
    }

}
