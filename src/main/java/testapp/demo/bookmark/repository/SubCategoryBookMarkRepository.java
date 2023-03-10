package testapp.demo.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testapp.demo.bookmark.dto.UserSubBookMarkDetailDto;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.bookmark.entity.SubCategoryBookMark;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryBookMarkRepository extends JpaRepository<SubCategoryBookMark,Long> {

    List<SubCategoryBookMark> findByMainCategoryAndMember(MainCategory mainCategory, Member member);


//    Optional<MainCategoryBookMark> findByMemberAndMainCategory(Member member,MainCategory mainCategory);


}
