package testapp.demo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testapp.demo.board.entity.Board;
import testapp.demo.board.entity.BoardBookmark;
import testapp.demo.board.entity.BoardLike;
import testapp.demo.board.mapper.BoardBookmarkMapper;
import testapp.demo.board.mapper.BoardLikeMapper;
import testapp.demo.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface BoardBookMarkRepository extends JpaRepository<BoardBookmark,Integer> {
    List<BoardBookmarkMapper> findByMember(Member member);

    Optional<BoardBookmark> findByBoardAndMember(Board board, Member member);


    void deleteByMemberAndBoard(Member member,Board board);

}
