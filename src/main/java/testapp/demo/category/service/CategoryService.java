package testapp.demo.category.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.category.entity.CategoryVo;

import java.util.List;

@Service
public interface CategoryService {
    ResponseEntity<CategoryVo> createCategoryList();

    ResponseEntity<List<CategoryVo>> getCategoryList(int categoryId);




}
