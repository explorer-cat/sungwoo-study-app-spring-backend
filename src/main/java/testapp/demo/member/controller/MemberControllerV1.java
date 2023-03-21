package testapp.demo.member.controller;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import testapp.demo.auth.JwtProvider;
import testapp.demo.auth.SecurityUtil;
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
     * @param data
     * @return
     * @title : 카카오 가입 계정 생성
     */
    @PostMapping("/login/signup")
    public ResponseEntity<UserInfoResponseDto> addUser(@RequestBody SignUpRequestDto data) {
        return memberService.signUpUser(data);
    }

    @GetMapping
    public ResponseEntity<UserInfoResponseDto> findUserEmail() {
        return new ResponseEntity<>(memberService.getUserEmail(SecurityUtil.getUserEmail()),HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity updateUser(@RequestBody UpdateUserRequestDto data) {
        try {
            memberService.updateUserInfo(data);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex,HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity withDrawalUser(@RequestBody WithDrawalRequestDto request) {
        try {
            memberService.withdrawalUser(request);
            return ResponseEntity.ok().build();
        } catch(IllegalStateException ex) {
            return new ResponseEntity(ex,HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
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

        System.out.println("accessTokenJsonData = " + accessTokenJsonData);

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


        //해당 이메일이 이미 가입되어있는지 조회함.
        if (users_kakao_data.get("email") != null) {
            MemberStatus = memberService.isMember(users_kakao_data.get("email").toString());
        }

        //이미 가입되어있는 경우
        if (MemberStatus) {
            userResponse = new LoginTokenResponseDto(1000, users_kakao_data, memberService.createToken(users_kakao_data.get("email").toString()));
        } else {
            userResponse = new LoginTokenResponseDto(1001, users_kakao_data, null);
        }
        //토큰 발급
        System.out.println("userResponse = " + userResponse);
        System.out.println("this is your token = " + memberService.createToken(users_kakao_data.get("email").toString()));
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
