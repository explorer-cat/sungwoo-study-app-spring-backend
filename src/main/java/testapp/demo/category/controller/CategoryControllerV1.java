package testapp.demo.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testapp.demo.category.dto.CreateCategoryRequest;
import testapp.demo.category.entity.Category;
import testapp.demo.category.service.CategoryService;
import testapp.demo.utils.ResDto;

@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryControllerV1 {

    @Autowired
    private CategoryService categoryService;

    public CategoryControllerV1(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * @title 카테고리 정보 조회
     * @param categoryId
     * @return
     */
    @GetMapping()
    public ResponseEntity<ResDto> category(@RequestParam(value = "category",required = false, defaultValue = "0") int categoryId) {
        return categoryService.getCategoryList(categoryId);
    }

    /**
     * @title 카테고리 생성
     * @return
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryRequest request) {
        return categoryService.createCategory(request);
    }

}
