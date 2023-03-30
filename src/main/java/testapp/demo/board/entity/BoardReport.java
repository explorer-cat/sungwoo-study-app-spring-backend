package testapp.demo.board.entity;

import lombok.*;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor    //첫번째 방법
@AllArgsConstructor
@Entity
@Builder
public class BoardReport {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(targetEntity = Board.class)
    @JoinColumn(name = "board_id")
    private Board board;
    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "user_email")
    private Member member;
    private String reason;
    private LocalDateTime createTime;

}
