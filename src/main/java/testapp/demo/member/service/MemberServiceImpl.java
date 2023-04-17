package testapp.demo.member.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import testapp.demo.auth.JwtProvider;
import testapp.demo.auth.SecurityUtil;
import testapp.demo.member.dto.*;
import testapp.demo.member.entity.Member;
import testapp.demo.member.repository.MemberRepository;
import testapp.demo.utils.TokenUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtProvider jwtProvider;
    private final String HTTP_REQUEST = "https://kapi.kakao.com/v2/user/me";
    private final String GRANT_TYPE= "authorization_code";
    private final String REDIRECT_URI= "http://explorer-cat-api.p-e.kr:3000/login/auth/code";
    private final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String CLIENT_ID = "ad874f9a277a8d0b35591cbf40f5bd82";
    private final String CLIENT_SECRET= "L9M7Xxh5XayyOM22BcMlllmzVe61wPzw";

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
        System.out.println("code = " + code);
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
        System.out.println("uriComponentsBuilder = " + uriComponentsBuilder);
        System.out.println("responseEntity = " + responseEntity);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }

        return "error";
    }

    public TokenResponse createToken(String userEmail)  {
        String token = jwtProvider.createToken(userEmail); // 토큰 생성
        System.out.println("tokenzzz = " + token);
        Claims claims = jwtProvider.parseJwtToken(token); // 토큰 검증

        TokenDataResponse tokenDataResponse = new TokenDataResponse(token, claims.getSubject(), claims.getIssuedAt().toString(), claims.getExpiration().toString());

        TokenResponse tokenResponse = new TokenResponse("200", "OK", tokenDataResponse);
        return tokenResponse;
    }

    @Override
    public UserInfoResponseDto getUserEmail(String email) {
        Member userInfo = memberRepository.findByEmail(email.toString());
        UserInfoResponseDto dto = new UserInfoResponseDto(userInfo);
        return dto;
    }

    @Override
    public void updateUserInfo(UpdateUserRequestDto request) {
        Member findMember = memberRepository.findByEmail(SecurityUtil.getUserEmail());

        //변경 시도 닉네임과 현재 닉네임이 동일한 경우
        if(findMember.getNickname().equals(request.getNickname())) {
            throw new IllegalArgumentException("현재 닉네임과 동일한 닉네임이 존재합니다.");
        }

        Optional<Member> checkNickName = memberRepository.findByNickname(request.getNickname());
        if(checkNickName.isPresent()) {
            throw new IllegalArgumentException("이미 사용하고 있는 닉네임 입니다.");
        }

        //닉네임 변경
        findMember.setNickname(request.getNickname());
        memberRepository.save(findMember);
    }

    @Override
    public Boolean isMember(String email) {
        Member member = memberRepository.findByEmail(email.toString());
        if(member != null) {
            return true;
        } else {
            return false;
        }
    }
    //사용자 가입
    @Override
    public ResponseEntity<UserInfoResponseDto> signUpUser(SignUpRequestDto req)  {
        // Check if email is already registered
        Member existingUser = memberRepository.findByEmail(req.getEmail());

        //해당 이메일은 이미 가입되어있음.
        if(existingUser != null) {
            return new ResponseEntity<>(new UserInfoResponseDto(null), HttpStatus.CONFLICT);
        }

        // Create a new user
        Member member = new Member();
        member.setEmail(req.getEmail());
        member.setNickname(req.getNickname());
        member.setThumbnailImage(req.getProfileImage());
        member.setBan(false);
        member.setLevel((byte) 0);
        memberRepository.save(member);

        return new ResponseEntity<>(new UserInfoResponseDto(member), HttpStatus.OK);
    }

    /**
     * @title 사용자 탈퇴
     * @param request
     */
    @Override
    public void withdrawalUser(WithDrawalRequestDto request) {
        //탈퇴사유는 무조건 존재해야함.
        if(!request.getReason().equals("")) {
            Member findMember = memberRepository.findByEmail(SecurityUtil.getUserEmail());
            findMember.setWithdrawal(true);
            findMember.setWithdrawal_reason(request.getReason());
            findMember.setWithdrawal_date(LocalDateTime.now());
            memberRepository.save(findMember);
        } else {
            throw new IllegalArgumentException("비정상적인 탈퇴 시도입니다.");
        }
    }
}
