package testapp.demo.user.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import testapp.demo.board.entity.BoardVo;
import testapp.demo.user.dto.UserInfoResponseDto;

import java.util.HashMap;

@Service
public interface UserService {
    String getAccessTokenJsonData(String code);

    JSONObject getKakaoUserInfo(String accessToken);

    BoardVo getUserEmail(String email);

}
