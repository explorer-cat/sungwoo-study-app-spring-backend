package testapp.demo.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testapp.demo.member.dto.UserInfoResponseDto;
import testapp.demo.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {
    Member findByEmail(String email);

    Optional<Member> findByNickname(String NickName);



}
