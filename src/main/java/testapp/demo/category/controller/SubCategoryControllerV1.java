package testapp.demo.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testapp.demo.category.dto.mainCategory.CreateMainCategoryRequest;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.dto.subCategory.CreateSubCategoryRequest;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.category.repository.SubCategoryRepository;
import testapp.demo.category.service.SubCategoryService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/category/sub")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubCategoryControllerV1 {

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping("/{mainCategoryId}")
    public ResponseEntity<List<SubCategoryResponseDTO>> getAllSubCategory(@PathVariable("mainCategoryId") long mainCategoryId) {
        try {
            //tjdrhd
            return new ResponseEntity<>(subCategoryService.getAllSubCategory(mainCategoryId), HttpStatus.OK);
        } catch (IllegalStateException e) {
            if (e.getMessage().equals("Empty")) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            System.out.println("error : " + ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/{mainCategoryId}")
    public ResponseEntity<SubCategoryResponseDTO> createSubCategory(
            @PathVariable("mainCategoryId") long mainCategoryId,
            @RequestBody CreateSubCategoryRequest request) {
        try {
            //todo 관리자 이메일 체크해야함.
            //SecurityUtil.getUserEmail();
            subCategoryService.createCategory(mainCategoryId, request);
            return ResponseEntity.created(null).build();
        } catch (IllegalStateException e) {
            if (e.getMessage().equals("exist")) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            } else if (e.getMessage().equals("notFound_Category")) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("")
    public ResponseEntity deleteSubCategory(
            @RequestParam("sub_id") List<Long> subCategories) {
        try {
            System.out.println("subCategories = " + subCategories);
            subCategoryService.removeCategory(subCategories);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{subCategoryId}")
    public ResponseEntity modifyCategory(@PathVariable("subCategoryId") long subCategoryId, @RequestBody CreateSubCategoryRequest request) {
        try{
            //todo 관리자 이메일 체크해야함.
            //SecurityUtil.getUserEmail();

            subCategoryService.modifyCategory(subCategoryId,request);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException ex) {
            //이미 동일한 이름이 존재할 경우 발생되는 상황
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (Exception ex) {
            System.out.println("ex = " + ex);
            return ResponseEntity.internalServerError().build();
        }
    }


}








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

