package testapp.demo.bookmark.controller;

import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testapp.demo.bookmark.dto.UserMainBookMarkDto;
import testapp.demo.bookmark.service.MainBookMarkService;
import testapp.demo.member.dto.TokenResponseNoData;
import testapp.demo.member.service.MemberServiceImpl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookMarkController {

    @Autowired
    private MainBookMarkService mainBookMarkService;

    @Autowired
    private MemberServiceImpl memberService;




    /**
     * @title 사용자가 북마크하고있는 메인카테고리 조회
     * @param jwt
     * @return
     */
    @GetMapping("/main/bookmark")
    public ResponseEntity<List<UserMainBookMarkDto>> getMainBookMark(@RequestHeader(value = "Authorization") String jwt) {
        //dto로 변환
        try {
            TokenResponseNoData tokenResponseNoData = memberService.checkToken(jwt);

            //사용자 토큰 검증에 성공했을 경우 사용자 정보를 반환합니다.
            if (tokenResponseNoData.getCode() == "200") {
                List<UserMainBookMarkDto> bookmarkList = mainBookMarkService.getMainBookMark("sqlstyle@kakao.com").stream()
                        .map(m -> new UserMainBookMarkDto(
                                m.getMainCategory().getId(),
                                m.getMainCategory().getName(),
                                m.getMainCategory().getCreateDate()))
                        .collect(Collectors.toList());
                return ResponseEntity.ok().body(bookmarkList);
            } else {
                //사용자 토큰기간이 만료됐거나 토큰이 변조되었을 경우
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
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
     * @title 메인카테고리 북마크 하기.
     * @param jwt
     * @param mainCategoryId
     * @return
     * @throws NotFound
     */
    @PostMapping("/main/bookmark/{mainCategoryId}")
    public ResponseEntity addMainBookMark(@RequestHeader(value = "Authorization") String jwt, @PathVariable("mainCategoryId") long mainCategoryId) throws NotFound {
        //토큰 검증.
        try {
            TokenResponseNoData tokenResponseNoData = memberService.checkToken(jwt);

            //사용자 토큰 검증에 성공했을 경우 사용자 정보를 반환합니다.
            if (tokenResponseNoData.getCode() == "200") {
                mainBookMarkService.addBookMark(tokenResponseNoData.getUserEmail(), mainCategoryId);
                return ResponseEntity.ok().build();
            } else {
                //사용자 토큰기간이 만료됐거나 토큰이 변조되었을 경우
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @title 북마크 메인카테고리 해제
     * @param jwt
     * @param mainCategoryId
     * @return
     * @throws NoSuchElementException
     */
    @DeleteMapping("/main/bookmark/{mainCategoryId}")
    public ResponseEntity removeMainBookMark(@RequestHeader(value = "Authorization") String jwt, @PathVariable("mainCategoryId") long mainCategoryId) throws NoSuchElementException {
        //토큰 검증.
        try {
            TokenResponseNoData tokenResponseNoData = memberService.checkToken(jwt);

            //사용자 토큰 검증에 성공했을 경우 사용자 정보를 반환합니다.
            if (tokenResponseNoData.getCode() == "200") {
                mainBookMarkService.removeBookMark(tokenResponseNoData.getUserEmail(), mainCategoryId);
                return ResponseEntity.ok().build();
            } else {
                //사용자 토큰기간이 만료됐거나 토큰이 변조되었을 경우
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (NoSuchElementException ex) {
            System.err.println(ex);
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            System.err.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
