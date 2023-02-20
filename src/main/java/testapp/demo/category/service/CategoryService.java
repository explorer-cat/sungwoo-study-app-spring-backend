package testapp.demo.category.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.category.dto.CreateCategoryRequest;
import testapp.demo.category.entity.Category;
import testapp.demo.utils.ResDto;

@Service
public interface CategoryService {
    ResponseEntity<Category> createCategory(CreateCategoryRequest request);

    ResponseEntity<ResDto> getCategoryList(int categoryId);




}
