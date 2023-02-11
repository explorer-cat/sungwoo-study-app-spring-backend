package testapp.demo.user.controller;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import testapp.demo.user.service.KakaoAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.json.JSONObject;
import testapp.demo.user.service.KakaoUserService;
import testapp.demo.user.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {

    private static KakaoUserService kakaoUserService;
    private static KakaoAuthService kakaoAuthService;
    private static UserService userService;

    @Autowired
    public UserControllerV1(KakaoUserService kakaoUserService, KakaoAuthService kakaoAuthService, UserService userService) {
        this.kakaoUserService = kakaoUserService;
        this.kakaoAuthService = kakaoAuthService;
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
    public String loginKakao(@RequestParam String code) throws JSONException {
        KakaoAuthService restJsonService = new KakaoAuthService();
        //access_token이 포함된 JSON String을 받아온다.
        String accessTokenJsonData = restJsonService.getAccessTokenJsonData(code);
        if(accessTokenJsonData=="error") {
            return "error";
        }
        //JSON String -> JSON Object
        JSONObject accessTokenJsonObject = new JSONObject(accessTokenJsonData);

        //access_token 추출
        String accessToken = accessTokenJsonObject.get("access_token").toString();

        //로그인 시도한 사용자의 정보를 가져와서 가입된 정보인지, 미가입자인지 확인한다.
        JSONObject kakao_users = kakaoUserService.getKakaoUserInfo(accessToken);
        System.out.println("kakao_users.get(\"kakao_account\")" + kakao_users.get("kakao_account"));

        if(kakao_users != null) {
            //사용자 이메일정보 디비에서 조회
//            userService.users(kakao_users.get("email"));
        }


        //미가입자일 경우




        return accessTokenJsonData;
    }

}
