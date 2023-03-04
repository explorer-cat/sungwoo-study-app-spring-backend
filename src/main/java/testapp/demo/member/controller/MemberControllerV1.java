package testapp.demo.member.controller;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import testapp.demo.auth.JwtProvider;
import testapp.demo.member.dto.*;
import testapp.demo.member.service.MemberService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import testapp.demo.utils.dto.ErrorResponse;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberControllerV1 {
    @Autowired
    private MemberService memberService;



    /**
     * @title : 카카오 가입 계정 생성
     * @param data
     * @return
     */
    @PostMapping("/login/signup")
    public ResponseEntity<UserInfoResponseDto> addUser(@RequestBody SignUpRequestDto data) {
        return memberService.signUpUser(data);
    }
    @GetMapping
    public ResponseEntity<UserInfoResponseDto> findUserEmail(@RequestHeader(value="Authorization") String jwt) {
        TokenResponseNoData tokenResponseNoData = memberService.checkToken(jwt);

        //사용자 토큰 검증에 성공했을 경우 사용자 정보를 반환합니다.
        if(tokenResponseNoData.getCode() == "200") {
            return new ResponseEntity<>(memberService.getUserEmail(tokenResponseNoData.getUserEmail()),HttpStatus.OK);
        } else {
            //사용자 토큰기간이 만료됐거나 토큰이 변조되었을 경우
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }




    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String email) {
        return null;
    }

    @PostMapping("/delete")
    public ResponseEntity<UserInfoResponseDto> deleteUser(@RequestBody Map<String,String> deleteUserData) {
        return memberService.deleteUserById(deleteUserData.get("email"));
    }

    //로그인 시도.
    @GetMapping("/login/kakao")
    public ResponseEntity<?> loginKakao(@RequestParam(required = false) String code) throws ParseException {
        LoginTokenResponseDto userResponse;
        JSONParser jsonParse = new JSONParser();
        String accessTokenJsonData = null;

        if (code == null) {
            return new ResponseEntity<>(new ErrorResponse(400, "Parameter 'code' is missing"), HttpStatus.BAD_REQUEST);
        } else {
            accessTokenJsonData = memberService.getAccessTokenJsonData(code);
        }

        if (accessTokenJsonData == null) {
            return new ResponseEntity<>(new ErrorResponse(500, "Failed to retrieve access token data"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (accessTokenJsonData.equals("error")) {
            return new ResponseEntity<>(new ErrorResponse(400, "Invalid authorization code"), HttpStatus.BAD_REQUEST);
        }

        JSONObject tokenData = (JSONObject) jsonParse.parse(accessTokenJsonData);

        String accessToken = tokenData.get("access_token").toString();
        String refreshToken = tokenData.get("refresh_token").toString();

        JSONObject users_kakao_data = (JSONObject) memberService.getKakaoUserInfo(accessToken).get("kakao_account");

        if (users_kakao_data == null || users_kakao_data.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse(404, "The requested user data was not found"), HttpStatus.NOT_FOUND);
        }

        Boolean MemberStatus = false;

        System.out.println("users_kakao_data = " + users_kakao_data);

        //해당 이메일이 이미 가입되어있는지 조회함.
        if(users_kakao_data.get("email") != null) {
            MemberStatus = memberService.isMember(users_kakao_data.get("email").toString());
        }
        //이미 가입되어있는 경우
        if(MemberStatus) {
            userResponse = new LoginTokenResponseDto(1000,users_kakao_data,memberService.createToken(users_kakao_data.get("email").toString()));
        }
        else {
            userResponse = new LoginTokenResponseDto(1001,users_kakao_data,null);
        }
        //토큰 발급
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }
}
