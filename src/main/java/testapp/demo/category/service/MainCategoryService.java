package testapp.demo.category.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.category.dto.mainCategory.CreateMainCategoryRequest;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;

import java.util.List;
import java.util.Map;

@Service
public interface MainCategoryService {



    //카테고리 생성
    MainCategoryResponseDTO createCategory(CreateMainCategoryRequest request);


    void modifyCategory(long mainCategoryId, CreateMainCategoryRequest request);

    //카테고리 삭제
    void removeCategory(long mainCategoryId) throws Exception;

    //카테고리 모두 찾기
    List<MainCategoryResponseDTO> findAllCategory();

    MainCategoryResponseDTO findCategory(long categoryId);

    List<Map<String,Object>> getSubCategories(long mainCategoryId);




//    ResponseEntity addBookMark(String userEmail, long mainCategory);



}
