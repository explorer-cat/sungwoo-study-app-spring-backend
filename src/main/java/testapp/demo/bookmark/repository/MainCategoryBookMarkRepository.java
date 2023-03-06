package testapp.demo.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.member.entity.Member;

import java.util.Optional;

@Repository
public interface MainCategoryBookMarkRepository extends JpaRepository<MainCategoryBookMark,Long> {
}
