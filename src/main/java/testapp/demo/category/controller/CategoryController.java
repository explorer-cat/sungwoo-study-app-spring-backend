package testapp.demo.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testapp.demo.category.entity.Category;
import testapp.demo.category.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/v1/category")
    //@RequestParam(value = "category",required = false, defaultValue = "0") category가 없어도 들어오게
    public ResponseEntity<List<Category>> getCategoryList(@RequestParam(value = "category",required = false, defaultValue = "0") int categoryId) {
        return categoryService.getCategoryList(categoryId);
    }

    @PostMapping("/api/v1/category")
    public ResponseEntity<List<Category>> createCategoryList() {
        return null;
    }

}
