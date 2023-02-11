package testapp.demo.user.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import testapp.demo.board.repository.BoardRepository;
import testapp.demo.user.dto.UserInfoResponseDto;
import testapp.demo.user.repository.UserRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@RequiredArgsConstructor
@Service
public class KakaoUserService {

    private final UserRepository userRepository;

    private final String HTTP_REQUEST = "https://kapi.kakao.com/v2/user/me";

    public JSONObject getKakaoUserInfo(String accessToken){
        JSONObject userInfoJson = null;

        try {
            // URI를 URL객체로 저장
            URL url = new URL(HTTP_REQUEST + "?access_token=" + accessToken);

            // 버퍼 데이터(응답 메세지)를 한 줄씩 읽어서 jsonData에 저장
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            String line;

            while((line = bf.readLine()) != null){
                 userInfoJson = new JSONObject(line);
            }

            return userInfoJson;
        } catch(Exception e) {
            return userInfoJson;
        }
    }



}
