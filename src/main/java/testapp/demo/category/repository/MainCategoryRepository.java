package testapp.demo.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.member.dto.UserInfoResponseDto;

import java.util.Optional;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory,Long> {

    Optional<MainCategory> findByName(String name);


}
