package testapp.demo.user.controller;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.json.JSONObject;
import testapp.demo.board.entity.BoardVo;
import testapp.demo.user.dto.UserInfoResponseDto;
import testapp.demo.user.repository.UserRepository;
import testapp.demo.user.service.UserService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {

    private UserService userService;

    @Autowired
    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String user() {
        return null;
    }

    @PostMapping
    public String addUser() {
        return null;
    }

    @GetMapping("/{userId}")
    public String findUser() {
        return null;
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
    public Map<?,?> loginKakao(@RequestParam String code) throws JSONException {

        String accessTokenJsonData = userService.getAccessTokenJsonData(code);
        if (accessTokenJsonData == "error") {
            return null;
        }
        //JSON String -> JSON Object
        JSONObject accessTokenJsonObject = new JSONObject(accessTokenJsonData);

        //access_token 추출
        String accessToken = accessTokenJsonObject.get("access_token").toString();

        //로그인 시도한 사용자의 정보를 가져와서 가입된 정보인지, 미가입자인지 확인한다.
        JSONObject kakao_users = userService.getKakaoUserInfo(accessToken).getJSONObject("kakao_account");
        //사용자 이메일
        String users_email = kakao_users.getString("email");

        BoardVo userInfo = userService.getUserEmail(users_email);


        //사용자 정보 없음 가입으로 진행
        if(userInfo == null) {
            System.out.println("kakao_users" + kakao_users);
            Map<String, JSONObject> loginInfo = new HashMap<>();
            loginInfo.put("data", kakao_users);
            return loginInfo;
        }
        //사용자 정보있음. 로그인 시켜주기
        else {
            Map<String, BoardVo> loginInfo = new HashMap<>();
            loginInfo.put("data", userInfo);
            return loginInfo;
        }
    }
}
