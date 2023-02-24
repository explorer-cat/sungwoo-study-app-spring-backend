package testapp.demo.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testapp.demo.category.dto.mainCategory.CreateMainCategoryRequest;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.service.MainCategoryServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/category/main")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainCategoryControllerV1 {

    @Autowired
    private MainCategoryServiceImpl mainCategoryService;


    /**
     * @title : 전체 메인 카테고리 정보 요청
     * @return
     */
    @GetMapping
    public ResponseEntity<List<MainCategoryResponseDTO>> getAllCategories() {
        List<MainCategoryResponseDTO> categoryList = mainCategoryService.findAllCategory();

       if (categoryList == null || categoryList.isEmpty()) {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryList);
    }

    /**
     * @title : 메인 카테고리 정보 단건 요청
     * @return
     */
    @GetMapping("/{mainCategoryId}")
    public ResponseEntity<MainCategoryResponseDTO> getCategory(@PathVariable ("mainCategoryId") long id) {
        try {
            return ResponseEntity.ok(mainCategoryService.findCategory(id));
        } catch (NullPointerException ex) {
            System.out.printf("error : MainCategory - getCategory() - Null");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * @title 메인 카테고리 생성
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<MainCategoryResponseDTO> createCategory(@RequestBody CreateMainCategoryRequest request) {
        try{
            MainCategoryResponseDTO category = mainCategoryService.createCategory(request);
            return new ResponseEntity<>(category, HttpStatus.CREATED);
        } catch (IllegalStateException ex) {
            //이미 동일한 이름이 존재할 경우 발생되는 상황
            System.out.println("exception error 발생 = " + ex);
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("{mainCategoryId}")
    public ResponseEntity removeCategory(@PathVariable ("mainCategoryId") long mainCategoryId) {
        try {
            mainCategoryService.removeCategory(mainCategoryId);
            return (ResponseEntity) ResponseEntity.ok();
        } catch(Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
