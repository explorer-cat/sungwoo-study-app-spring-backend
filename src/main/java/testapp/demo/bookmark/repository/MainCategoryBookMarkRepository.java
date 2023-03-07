package testapp.demo.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testapp.demo.board.entity.Board;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.member.entity.Member;

import java.util.Optional;

@Repository
public interface MainCategoryBookMarkRepository extends JpaRepository<MainCategoryBookMark,Long> {


    Optional<MainCategoryBookMark> findByMemberAndMainCategory(Member member,MainCategory mainCategory);

    void deleteByMemberAndMainCategory(Member member, MainCategory mainCategory);

}
