package testapp.demo.board.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import testapp.demo.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Override
    public String toString() {
        return "BoardLike{" +
                "id=" + id +
                ", member=" + member +
//                ", board=" + board +
                ", createdDate=" + createdDate +
                '}';
    }

    //    private void cacelBoardLike(Board board) {
//        this.board = board;
//        board.getBoardLike().remove(this);
//    }

}
