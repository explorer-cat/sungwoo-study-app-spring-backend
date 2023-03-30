package testapp.demo.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testapp.demo.category.entity.SubCategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    List<SubCategory> findByMainCategoryId(long mainCategoryId);

    Optional<SubCategory> findByNameOrId(String name,long subCategoryId);


}
