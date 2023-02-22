package testapp.demo.member.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import testapp.demo.member.dto.LoginTokenResponseDto;
import testapp.demo.member.dto.SignUpRequestDto;
import testapp.demo.member.dto.UserInfoResponseDto;
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

    private MemberService userService;

    @Autowired
    public MemberControllerV1(MemberService memberService) {
        this.userService = userService;
    }

    /**
     * @title : addUser kakaoUser SignUp
     * @param data
     * @return
     */
    @PostMapping("/login/signup")
    public ResponseEntity<UserInfoResponseDto> addUser(@RequestBody SignUpRequestDto data) {
        return userService.signUpUser(data);
    }
    @GetMapping
    public UserInfoResponseDto findUserEmail(@RequestParam String email) {
        UserInfoResponseDto res = userService.getUserEmail(email);
        System.out.println("res = " + res);
        return res;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String email) {
        return null;
    }

    @PostMapping("/delete")
    public ResponseEntity<UserInfoResponseDto> deleteUser(@RequestBody Map<String,String> deleteUserData) {
        return userService.deleteUserById(deleteUserData.get("email"));
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
            accessTokenJsonData = userService.getAccessTokenJsonData(code);
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

        JSONObject users_kakao_data = (JSONObject) userService.getKakaoUserInfo(accessToken).get("kakao_account");

        if (users_kakao_data == null || users_kakao_data.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse(404, "The requested user data was not found"), HttpStatus.NOT_FOUND);
        }

        Boolean MemberStatus = false;

        if(users_kakao_data.get("email") != null) {
            MemberStatus = userService.isMember(users_kakao_data.get("email").toString());
        }

        //이미 가입되어있는 경ㅖ
        if(MemberStatus) {
            userResponse = new LoginTokenResponseDto(1000,users_kakao_data,refreshToken,accessToken);
        }
        else {
            userResponse = new LoginTokenResponseDto(1001,users_kakao_data,refreshToken,accessToken);
        }
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

}
