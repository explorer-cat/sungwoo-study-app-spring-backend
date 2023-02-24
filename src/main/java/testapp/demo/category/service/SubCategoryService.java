package testapp.demo.category.service;

import org.springframework.stereotype.Service;
import testapp.demo.category.dto.mainCategory.CreateMainCategoryRequest;
import testapp.demo.category.dto.subCategory.CreateSubCategoryRequest;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;

import java.util.List;

@Service
public interface SubCategoryService {


    //카테고리 생성
    SubCategoryResponseDTO createCategory(long mainCategoryId, CreateSubCategoryRequest request);

    SubCategoryResponseDTO removeCategory(long mainCategoryId);

    //카테고리 모두 찾기
    List<SubCategoryResponseDTO> getAllSubCategory(long mainCategoryId);



}
