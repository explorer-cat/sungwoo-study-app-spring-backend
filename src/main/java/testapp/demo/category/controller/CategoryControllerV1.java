package testapp.demo.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testapp.demo.category.entity.CategoryVo;
import testapp.demo.category.service.CategoryService;

import java.util.List;

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

    @GetMapping()
    //@RequestParam(value = "category",required = false, defaultValue = "0") category가 없어도 들어오게
    public ResponseEntity<List<CategoryVo>> getCategoryList(@RequestParam(value = "category",required = false, defaultValue = "0") int categoryId) {
        return categoryService.getCategoryList(categoryId);
    }

    @PostMapping("/api/v1/category")
    public ResponseEntity<List<CategoryVo>> createCategoryList() {
        return null;
    }

}
