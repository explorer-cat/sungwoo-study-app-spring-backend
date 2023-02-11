package testapp.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testapp.demo.board.entity.BoardVo;
import testapp.demo.user.entity.UserVo;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserVo,Integer> {
    BoardVo findByEmail(String email);
}
