package testapp.demo.member.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.member.dto.SignUpRequestDto;
import testapp.demo.member.dto.TokenResponse;
import testapp.demo.member.dto.TokenResponseNoData;
import testapp.demo.member.dto.UserInfoResponseDto;

@Service
public interface MemberService {
    String getAccessTokenJsonData(String code);

    JSONObject getKakaoUserInfo(String accessToken);

    UserInfoResponseDto getUserEmail(String email);

    Boolean isMember(String email);

    ResponseEntity<UserInfoResponseDto> signUpUser(SignUpRequestDto data);

    ResponseEntity<UserInfoResponseDto> deleteUserById(String email);

    TokenResponse createToken(String userEmail);

    TokenResponseNoData checkToken(String jwt_token);
}
