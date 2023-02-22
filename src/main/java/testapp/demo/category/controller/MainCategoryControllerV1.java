package testapp.demo.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testapp.demo.category.dto.mainCategory.MainCategoryDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.service.MainCategoryService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/category/main")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainCategoryControllerV1 {

    @Autowired
    private MainCategoryService mainCategoryService;


    @GetMapping
    public ResponseEntity<List<MainCategoryDTO>> getAllCategories() {
        List<MainCategoryDTO> categoryList = mainCategoryService.findAllCategory();

       if (categoryList == null || categoryList.isEmpty()) {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryList);
    }
//    @Autowired
//    public CategoryController(SubCategoryService subCategoryService) {
//        this.subCategoryService = subCategoryService;
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<?>> allSubCategory() {
//        return subCategoryService.findAllCategory();
//    }
//
//    /**
//     * @title 카테고리 생성
//     * @return
//     */
//    @PostMapping
//    public ResponseEntity<SubCategory> createSubCategory(@RequestBody CreateSubCategoryRequest request) {
//        return subCategoryService.createCategory(request);
//    }
//    @DeleteMapping("/{categoryId}")
//    public ResponseEntity<ResDto> removeSubCategory(@PathVariable("categoryId") int id) {
//        return subCategoryService.removeCategory(id);
//    }
//
//    @PutMapping("/{categoryId}")
//    public ResponseEntity<Optional<?>> updateSubCategory(@PathVariable("categoryId") int id, @RequestBody UpdateSubCategoryRequest data) {
//        System.out.println("data" + data.getCategoryName());
//        return subCategoryService.updateByCategory(id, data.getCategoryName());
//    }
}
