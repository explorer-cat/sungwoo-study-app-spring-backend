package testapp.demo.bookmark.entity;


import lombok.*;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "mainCategoryBookMark")
//무분별한 create를 막기위한 생성자 호출 방지용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MainCategoryBookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "main_bookmark_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_category_ID")
    private MainCategory mainCategory;

    private LocalDateTime createDate;

    private void setMember(Member member) {
        this.member = member;
        member.getMainCategoryBookMark().add(this);
    }

    public List<MainCategoryBookMark> getMember(Member member) {
        this.member = member;
        return member.getMainCategoryBookMark();
    }

    private void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
        mainCategory.getMainCategoryBookMarkList().add(this);
    }

    //북마크 생성
    public static MainCategoryBookMark createMainBookMark(Member member, MainCategory mainCategory) {
        MainCategoryBookMark mainCategoryBookMark = new MainCategoryBookMark();
        mainCategoryBookMark.setMember(member);
        mainCategoryBookMark.setMainCategory(mainCategory);
        mainCategoryBookMark.setCreateDate(LocalDateTime.now());

        return mainCategoryBookMark;
    }





    //북마크 취소
}
