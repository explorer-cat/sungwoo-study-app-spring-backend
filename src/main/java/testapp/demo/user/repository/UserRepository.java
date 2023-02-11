package testapp.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.entity.BoardVo;
import testapp.demo.user.entity.UserVo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserVo,Integer> {
    Optional findByEmail(String email);


}
