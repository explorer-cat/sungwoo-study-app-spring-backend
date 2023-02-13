package testapp.demo.user.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import testapp.demo.board.entity.BoardVo;
import testapp.demo.user.dto.SignUpRequestDto;
import testapp.demo.user.dto.UserInfoResponseDto;
import testapp.demo.user.entity.UserVo;
import testapp.demo.user.repository.UserRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final String HTTP_REQUEST = "https://kapi.kakao.com/v2/user/me";
    private final String GRANT_TYPE= "authorization_code";
    private final String CLIENT_ID = "ad874f9a277a8d0b35591cbf40f5bd82";
    private final String REDIRECT_URI= "http://sungwoo-net.p-e.kr:3000/login/auth/code";
    private final String CLIENT_SECRET= "L9M7Xxh5XayyOM22BcMlllmzVe61wPzw";
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
                System.out.println("line = " + line);
                JSONParser jsonParse = new JSONParser();
                userInfoJson = (JSONObject) jsonParse.parse(line);
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
    public UserInfoResponseDto getUserEmail(String email) {
        HashMap<String,Object> users = new HashMap<>();//HashMap생성
        UserInfoResponseDto userInfo = userRepository.findByEmail(email);
        return userInfo;
    }
    @Override
    public Boolean isMember(String email) {
        return false;
    }


    //사용자 가입
    @Override
    public ResponseEntity<UserVo> signUpUser(SignUpRequestDto req)  {
        UserVo userVo = new UserVo();
        userVo.setUid(req.getUid());
        userVo.setEmail(req.getEmail());
        userVo.setNickname(req.getNickname());
        userVo.setThumbnailImage(req.getProfileImage());
        userVo.setBan(false);
        userVo.setAdmin(false);
        return new ResponseEntity<>(userRepository.save(userVo), HttpStatus.OK);
    }
}
