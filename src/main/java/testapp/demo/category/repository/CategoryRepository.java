package testapp.demo.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testapp.demo.category.entity.CategoryVo;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryVo,Long> {

    List<CategoryVo> findByCategoryId(int categoryId);
}
