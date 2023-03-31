package testapp.demo.category.service;

import org.springframework.stereotype.Service;
import testapp.demo.category.dto.mainCategory.CreateMainCategoryRequest;
import testapp.demo.category.dto.subCategory.CreateSubCategoryRequest;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public interface SubCategoryService {


    //카테고리 생성
    void createCategory(long mainCategoryId, CreateSubCategoryRequest request);

    void removeCategory(List<Long> removeList) throws Exception;

    void modifyCategory(long subCategoryId, CreateSubCategoryRequest request);

    //카테고리 모두 찾기
    List<SubCategoryResponseDTO> getAllSubCategory(long mainCategoryId);



}
