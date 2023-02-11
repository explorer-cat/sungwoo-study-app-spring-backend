package testapp.demo.user.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class KakaoAuthService {
    private final String GRANT_TYPE= "authorization_code";
    private final String CLIENT_ID = "ad874f9a277a8d0b35591cbf40f5bd82";
    private final String REDIRECT_URI= "http://localhost:8080/api/v1/users/login/kakao";
    private final String CLIENT_SECRET= "L9M7Xxh5XayyOM22BcMlllmzVe61wPzw";
    private final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";

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
}