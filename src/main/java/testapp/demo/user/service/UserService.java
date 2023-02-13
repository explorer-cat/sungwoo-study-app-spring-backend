package testapp.demo.user.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.board.entity.BoardVo;
import testapp.demo.user.dto.SignUpRequestDto;
import testapp.demo.user.dto.UserInfoResponseDto;
import testapp.demo.user.entity.UserVo;

import java.util.HashMap;
import java.util.Map;

@Service
public interface UserService {
    String getAccessTokenJsonData(String code);

    JSONObject getKakaoUserInfo(String accessToken);

    UserInfoResponseDto getUserEmail(String email);

    Boolean isMember(String email);

    ResponseEntity<UserVo> signUpUser(SignUpRequestDto data);

}
