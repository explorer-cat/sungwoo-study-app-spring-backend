package testapp.demo.member.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.member.dto.*;

@Service
public interface MemberService {
    String getAccessTokenJsonData(String code);

    JSONObject getKakaoUserInfo(String accessToken);

    UserInfoResponseDto getUserEmail(String email);

    void updateUserInfo(UpdateUserRequestDto request);

    Boolean isMember(String email);

    ResponseEntity<UserInfoResponseDto> signUpUser(SignUpRequestDto data);

    void withdrawalUser(WithDrawalRequestDto request);

    TokenResponse createToken(String userEmail);

//    TokenResponseNoData checkToken(String jwt_token);
}
