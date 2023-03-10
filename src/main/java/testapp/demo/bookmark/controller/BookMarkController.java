package testapp.demo.bookmark.controller;

import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testapp.demo.auth.SecurityUtil;
import testapp.demo.bookmark.dto.UserMainBookMarkDto;
import testapp.demo.bookmark.dto.UserSubBookMarkDto;
import testapp.demo.bookmark.service.BookMarkService;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookMarkController {

    @Autowired
    private BookMarkService bookMarkService;



    /**
     * @param jwt
     * @return
     * @title 사용자가 북마크하고있는 메인카테고리 조회
     */
    @GetMapping("/main/bookmark")
    public ResponseEntity<List<UserMainBookMarkDto>> getMainBookMark() {
        //dto로 변환
        try {
            List<UserMainBookMarkDto> bookmarkList = bookMarkService.getMainBookMark(SecurityUtil.getUserEmail()).stream()
                    .map(m -> new UserMainBookMarkDto(
                            m.getMainCategory().getId(),
                            m.getMainCategory().getName(),
                            m.getMainCategory().getCreateDate()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(bookmarkList);
        } catch (MalformedJwtException ex) {
            //사용자 토큰기간이 만료됐거나 토큰이 변조되었을 경우
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            System.err.println(ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * @param mainCategoryId
     * @return
     * @throws NotFound
     * @title 메인카테고리 북마크 하기.
     */
    @PostMapping("/main/bookmark/{mainCategoryId}")
    public ResponseEntity addMainBookMark(@PathVariable("mainCategoryId") long mainCategoryId) {
        //토큰 검증.
        try {
            //사용자 토큰 검증에 성공했을 경우 사용자 정보를 반환합니다.
            bookMarkService.addBookMark(SecurityUtil.getUserEmail(), mainCategoryId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param mainCategoryId
     * @return
     * @throws NoSuchElementException
     * @title 북마크 메인카테고리 해제
     */
    @DeleteMapping("/main/bookmark/{mainCategoryId}")
    public ResponseEntity removeMainBookMark(@PathVariable("mainCategoryId") long mainCategoryId) throws NoSuchElementException {
        //토큰 검증.
        try {
            bookMarkService.removeBookMark(SecurityUtil.getUserEmail(), mainCategoryId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException ex) {
            System.err.println(ex);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            System.err.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sub/bookmark")
    public ResponseEntity<List<UserSubBookMarkDto>>getSubBookMark(@RequestParam(name ="option", required = true) String option) {
        try {
            String userEmail = SecurityUtil.getUserEmail();

            System.out.println("option = " + option);
            if(option.equals("all")) {
                return new ResponseEntity<>(bookMarkService.getAllSubBookMark(userEmail),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(bookMarkService.getSelctedSubBookMark(userEmail),HttpStatus.OK);
            }
        } catch (MalformedJwtException ex) {
            //사용자 토큰기간이 만료됐거나 토큰이 변조되었을 경우
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            System.err.println(ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * @param subCategoryId
     * @return
     * @throws NotFound
     * @title 서브카테고리 북마크 하기.
     */
    @PostMapping("/sub/bookmark/{subCategoryId}")
    public ResponseEntity addSubBookMark(@PathVariable("subCategoryId") long subCategoryId) {
        //토큰 검증.
        try {
            //사용자 토큰 검증에 성공했을 경우 사용자 정보를 반환합니다.
            bookMarkService.addSubCategoryBookMark(SecurityUtil.getUserEmail(), subCategoryId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return
     * @throws NoSuchElementException
     * @title 북마크 메인카테고리 해제
     */
    @DeleteMapping("/sub/bookmark/{subCategoryId}")
    public ResponseEntity removeSubBookMark(@PathVariable("subCategoryId") long subCategoryId) throws NoSuchElementException {
        //토큰 검증.
        try {
            bookMarkService.removeSubBookMark(SecurityUtil.getUserEmail(), subCategoryId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException ex) {
            System.err.println(ex);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            System.err.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
