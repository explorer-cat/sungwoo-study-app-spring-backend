package testapp.demo.category.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.category.dto.subCategory.CreateSubCategoryRequest;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.utils.ResDto;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {


    //카테고리 생성
    ResponseEntity<SubCategory> createCategory(CreateSubCategoryRequest request);

    //카테고리 삭제
    ResponseEntity<ResDto> removeCategory(int categoryId);

    //카테고리 찾기
    ResponseEntity<SubCategory> findByCategory(int categoryId);

    //카테고리 수정
    ResponseEntity<Optional<?>> updateByCategory(int categoryId, String categoryName);

    //카테고리 모두 찾기
    List<?> findAllCategory();



}
