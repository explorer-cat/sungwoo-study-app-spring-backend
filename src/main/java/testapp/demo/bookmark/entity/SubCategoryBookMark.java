package testapp.demo.bookmark.entity;


import lombok.*;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "subCategoryBookMark")
//무분별한 create를 막기위한 생성자 호출 방지용
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SubCategoryBookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "sub_bookmark_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_category_ID")
    private MainCategory mainCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_ID")
    private SubCategory subCategory;

    private LocalDateTime createDate;

    @Override
    public String toString() {
        return "SubCategoryBookMark{" +
                "id=" + id +
                ", member=" + member +
                ", subCategory=" + subCategory +
                ", createDate=" + createDate +
                '}';
    }

    //북마크 취소
}
