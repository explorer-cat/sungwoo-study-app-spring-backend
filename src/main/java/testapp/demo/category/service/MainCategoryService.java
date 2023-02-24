package testapp.demo.category.service;

import org.springframework.stereotype.Service;
import testapp.demo.category.dto.mainCategory.CreateMainCategoryRequest;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;

import java.util.List;

@Service
public interface MainCategoryService {



    //카테고리 생성
    MainCategoryResponseDTO createCategory(CreateMainCategoryRequest request);

    //카테고리 삭제
    void removeCategory(long mainCategoryId) throws Exception;

    //카테고리 모두 찾기
    List<MainCategoryResponseDTO> findAllCategory();

    MainCategoryResponseDTO findCategory(long categoryId);





}
