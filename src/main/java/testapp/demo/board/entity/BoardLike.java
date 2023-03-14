package testapp.demo.board.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import testapp.demo.auth.SecurityUtil;
import testapp.demo.board.mapper.BoardLikeMapper;
import testapp.demo.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor    //첫번째 방법
@Entity
public class BoardLike {

    @Id
    @GeneratedValue
    @Column(name="like_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_ID")
    private Board board;

    private LocalDateTime createdDate;


    private void setBoard(Board board) {
        this.board = board;
        board.getBoardLike().add(this);
    }

    private void setMember(Member member) {
        this.member = member;
        member.getBoardLikes().add(this);
    }

    public static BoardLike addBoardLike(Board board, Member member) {
        BoardLike boardLike = new BoardLike();
        boardLike.setBoard(board);
        boardLike.setMember(member);
        boardLike.setCreatedDate(LocalDateTime.now());
        return boardLike;
    }

    public Set<Long> getUserLikesPostList(Member member, List<BoardLikeMapper> board_like_list) {
        //위에서 가져온값들을 Id만 따로 가져와서 Set에 저장
        Set<Long> result = new HashSet<>();
        List<BoardLikeMapper> boardLikeList = board_like_list;

        for (BoardLikeMapper boardLikeMapper : boardLikeList) {
            result.add(boardLikeMapper.getBoardId());
        }
        return result;
    }

    public Map<String, Object> setUserLikePost(Board board, Set<Long> board_like_list) {
        Map<String, Object> board_like_info = new HashMap<>();
        board_like_info.put("total_like_count", board.getBoardLike().size());
        //사용자가 좋아요 한 게시글이 해당 게시글인지 확인해서 true false 반환.
        board_like_info.put("user_like_status",
                //토큰이 없는 비로그인 사용자인 경우 무조건 false
                SecurityUtil.getUserEmail().equals("anonymousUser") ? false : board_like_list.contains(board.getId()));
        return board_like_info;
    }


}
