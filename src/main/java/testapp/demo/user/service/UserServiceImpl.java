package testapp.demo.user.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import testapp.demo.board.entity.BoardVo;
import testapp.demo.user.repository.UserRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final String HTTP_REQUEST = "https://kapi.kakao.com/v2/user/me";
    private final String GRANT_TYPE= "authorization_code";
    private final String CLIENT_ID = "";
    private final String REDIRECT_URI= "http://localhost:8080/api/v1/users/login/kakao";
    private final String CLIENT_SECRET= "";
    private final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    @Override
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

    @Override
    public String getAccessTokenJsonData(String code){
        RestTemplate restTemplate = new RestTemplate();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity request = new HttpEntity(headers);

        // URI 빌더 사용
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(TOKEN_URL)
                .queryParam("grant_type", GRANT_TYPE)
                .queryParam("client_id", CLIENT_ID)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("code", code)
                .queryParam("client_secret", CLIENT_SECRET);

        // 요청 URI과 헤더를 같이 전송
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.POST,
                request,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return "error";
    }

    @Override
    public BoardVo getUserEmail(String email) {
        HashMap<String,Object> users = new HashMap<>();//HashMap생성
        BoardVo userInfo = userRepository.findByEmail(email);
        return userInfo;
    }
}
