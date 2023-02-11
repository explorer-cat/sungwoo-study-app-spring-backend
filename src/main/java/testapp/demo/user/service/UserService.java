package testapp.demo.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.user.entity.UserVo;
import testapp.demo.user.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public Map<String , Objects> users(String email) {
        HashMap<String,Object> users = new HashMap<String,Object>();//HashMap생성
        Optional userInfo = userRepository.findByEmail(email);
        if(userInfo.isPresent()) {
            Object o = userInfo.get();
            System.out.println("o = " + o);
        } else {
            Object o = userInfo.get();
            System.out.println("o = " + o);

        }
        System.out.println("email = " + email);
        return null;
    }
}
