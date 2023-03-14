package testapp.demo.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import testapp.demo.auth.SecurityUtil;
import testapp.demo.board.mapper.BoardBookmarkMapper;
import testapp.demo.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor    //첫번째 방법
@Entity
public class BoardBookmark {

    @Id
    @GeneratedValue
    @Column(name="board_bookmark_id")
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
        board.getBoardBookmarks().add(this);
    }

    private void setMember(Member member) {
        this.member = member;
        member.getBoardBookmarks().add(this);
    }

    public static BoardBookmark addBoardBookmark(Board board, Member member) {
        BoardBookmark boardBookmark = new BoardBookmark();
        boardBookmark.setBoard(board);
        boardBookmark.setMember(member);
        boardBookmark.setCreatedDate(LocalDateTime.now());
        return boardBookmark;
    }


    public Set<Long> getUserBookmarkPost(Member member, List<BoardBookmarkMapper> mapper) {
        //위에서 가져온값들을 Id만 따로 가져와서 Set에 저장
        Set<Long> board_bookmark_list = new HashSet<>();

        //사용자가 좋아요 해놓은 게시글ID 값들만 저장.
        for (BoardBookmarkMapper board_bookmark_id : mapper) {
            board_bookmark_list.add(board_bookmark_id.getBoardId());
        }
        return board_bookmark_list;
    }


    public Map<String, Object> setUserBookmarkPost(Board board, Set<Long> board_bookmark_list) {
        Map<String, Object> board_bookmark_info = new HashMap<>();
        board_bookmark_info.put("total_bookmark_count", board.getBoardBookmarks().size());
        //사용자가 좋아요 한 게시글이 해당 게시글인지 확인해서 true false 반환.
        board_bookmark_info.put("user_bookmark_status",
                //토큰이 없는 비로그인 사용자인 경우 무조건 false
                SecurityUtil.getUserEmail().equals("anonymousUser") ? false : board_bookmark_list.contains(board.getId()));
        System.out.println("board_bookmark_info = " + board_bookmark_info);
        return board_bookmark_info;
    }

//
//    @Override
//    public String toString() {
//        return "BoardLike{" +
//                "id=" + id +
//                ", member=" + member +
////                ", board=" + board +
//                ", createdDate=" + createdDate +
//                '}';
//    }

    //    private void cacelBoardLike(Board board) {
//        this.board = board;
//        board.getBoardLike().remove(this);
//    }

}
