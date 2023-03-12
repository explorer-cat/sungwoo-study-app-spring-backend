package testapp.demo.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import testapp.demo.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

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
