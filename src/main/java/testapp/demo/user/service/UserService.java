package testapp.demo.user.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.user.dto.SignUpRequestDto;
import testapp.demo.user.dto.UserInfoResponseDto;

@Service
public interface UserService {
    String getAccessTokenJsonData(String code);

    JSONObject getKakaoUserInfo(String accessToken);

    UserInfoResponseDto getUserEmail(String email);

    Boolean isMember(String email);

    ResponseEntity<UserInfoResponseDto> signUpUser(SignUpRequestDto data);

    ResponseEntity<UserInfoResponseDto> deleteUserById(String email);

}
