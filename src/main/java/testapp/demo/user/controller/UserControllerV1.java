package testapp.demo.user.controller;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import testapp.demo.board.entity.BoardVo;
import testapp.demo.user.dto.LoginTokenResponseDto;
import testapp.demo.user.dto.SignUpRequestDto;
import testapp.demo.user.dto.UserInfoResponseDto;
import testapp.demo.user.repository.UserRepository;
import testapp.demo.user.service.UserService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserControllerV1 {

    private UserService userService;

    @Autowired
    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }



    @PostMapping
    public String addUser() {
        return null;
    }

    @GetMapping
    public UserInfoResponseDto findUserEmail(@RequestParam String email) {
        UserInfoResponseDto res = userService.getUserEmail(email);
        System.out.println("res = " + res);
        return res;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable Long userId) {
        return null;
    }


    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        return null;
    }


    //로그인 시도.
    @GetMapping("/login/kakao")
    public ResponseEntity<?> loginKakao(@RequestParam String code) throws ParseException {
        LoginTokenResponseDto userResponse;
        JSONParser jsonParse = new JSONParser();

        String accessTokenJsonData = userService.getAccessTokenJsonData(code);

        if (accessTokenJsonData == "error") {
            return null;
        }
        JSONObject tokenData = (JSONObject) jsonParse.parse(accessTokenJsonData);

        String accessToken = tokenData.get("access_token").toString();
        String refreshToken = tokenData.get("refresh_token").toString();

        JSONObject users_kakao_data = (JSONObject) userService.getKakaoUserInfo(accessToken).get("kakao_account");
        Boolean MemberStatus = userService.isMember(users_kakao_data.get("email").toString());

        //이미 가입되어있는 경ㅖ
        if(MemberStatus) {
            userResponse = new LoginTokenResponseDto(1000,users_kakao_data,refreshToken,accessToken);
        }
        else {
            userResponse = new LoginTokenResponseDto(1001,users_kakao_data,refreshToken,accessToken);
        }
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    @PostMapping("/login/signup")
    public ResponseEntity<UserInfoResponseDto> SignUp(@RequestBody SignUpRequestDto data) {
        userService.signUpUser(data);
        return null;
    }
}
