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
import testapp.demo.member.dto.TokenResponseNoData;
import testapp.demo.member.service.MemberServiceImpl;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardControllerV1 {
    @Autowired private BoardService boardService;
    @Autowired private MemberServiceImpl memberService;


    /**
     * @param subCategoryId
     * @return
     * @title 해당 전체 카테고리 게시글 모두 조회
     */
    @GetMapping("/{subCategoryId}")
    public ResponseEntity<List<BoardResponseDto>> getAllPostList(@RequestHeader(value = "Authorization",required = false) String jwt, @PathVariable("subCategoryId") long subCategoryId) {
        System.out.println("jwt = " + jwt);
        try {
            boolean isLoggined = false;
            BoardResponseDto dto = new BoardResponseDto();
            TokenResponseNoData token = null;
            //비회원상태
            if(jwt != null) {
                //jwt 토큰이 있는 경우 토큰 검증 시작.
                token = memberService.checkToken(jwt);

                //사용자 토큰 검증에 성공했을 경우 사용자 정보를 반환합니다.
                if(token.getCode() == "200") {
                    isLoggined = true;
                } else {
                    //사용자 토큰기간이 만료됐거나 토큰이 변조되었을 경우
                    return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
                }
            }
            List<Board> allPost = boardService.getAllPost(subCategoryId);

            //게시글이 하나도 없는 경우:
            if(allPost == null) {
                return ResponseEntity.notFound().build();
            }

            //로그인 되어있는 사용자라면 게시글의 좋아요 여부를 체크해야하기 때문에 아래 DTO 반환
            List result = new ArrayList();

            if(isLoggined) {
                for (Board v : allPost) {
                    result.add(dto.fromEntity(v,token.getUserEmail()));
                }
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else {
                //비로그인 회원 좋아요 체크 여부 확인 없이 반환
                for (Board v : allPost) {
                    result.add(dto.fromEntity(v,null));
                }
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
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
    public ResponseEntity<BoardResponseDto> getPost(@RequestHeader(value = "Authorization",required = false) String jwt, @PathVariable("subCategoryId") long subCategoryId, @PathVariable("postId") long postId) {
        try {
            BoardResponseDto dto = new BoardResponseDto();
            TokenResponseNoData token = null;
            boolean isLoggined = false;
            //jwt 값이 없다면 로그인하지 않은 사용자의 게시글 요청
            if(jwt == null) {
                isLoggined = false;
            } else {
                //jwt 토큰이 있는 경우 토큰 검증 시작.
                token = memberService.checkToken(jwt);

                //사용자 토큰 검증에 성공했을 경우 사용자 정보를 반환합니다.
                if(token.getCode() == "200") {
                    isLoggined = true;
                } else {
                    //사용자 토큰기간이 만료됐거나 토큰이 변조되었을 경우
                    return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
                }
            }
            //게시글 조회
            Optional<Board> post = boardService.getPost(subCategoryId, postId);

            //로그인 되어있는 사용자라면 게시글의 좋아요 여부를 체크해야하기 때문에 아래 DTO 반환
            if(isLoggined && token.getUserEmail().length() > 0) {
                return new ResponseEntity<>(dto.fromEntity(post.get(),token.getUserEmail()), HttpStatus.OK);
            }
            else {
                //비로그인 회원 좋아요 체크 여부 확인 없이 반환
                return new ResponseEntity<>(dto.fromEntity(post.get(),null),HttpStatus.OK);
            }
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


}
