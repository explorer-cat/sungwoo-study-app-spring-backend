package testapp.demo.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/category/sub")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubCategoryControllerV1 {
//
//
//    private SubCategoryService subCategoryService;
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
