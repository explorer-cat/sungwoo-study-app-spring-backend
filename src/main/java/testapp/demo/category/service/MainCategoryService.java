package testapp.demo.category.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.category.dto.mainCategory.MainCategoryDTO;
import testapp.demo.category.dto.subCategory.CreateSubCategoryRequest;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.category.repository.MainCategoryRepository;
import testapp.demo.utils.ResDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MainCategoryService implements CategoryService {
    private final MainCategoryRepository mainCategoryRepository;

    public MainCategoryService(MainCategoryRepository mainCategoryRepository) {
        this.mainCategoryRepository = mainCategoryRepository;
    }

    @Override
    public ResponseEntity<SubCategory> createCategory(CreateSubCategoryRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ResDto> removeCategory(int categoryId) {
        return null;
    }

    @Override
    public ResponseEntity<SubCategory> findByCategory(int categoryId) {
        return null;
    }

    @Override
    public ResponseEntity<Optional<?>> updateByCategory(int categoryId, String categoryName) {
        return null;
    }

    @Override
    public List<MainCategoryDTO> findAllCategory() {
        List<MainCategory> categories = mainCategoryRepository.findAll();
        List<MainCategoryDTO> result = new ArrayList<>();

        //DB에서 받은 값들을 DTO에 맵핑 시켜주는 작업
        //DB에서 받은 값들이 없다면 반복문을 돌지않고 빈 리스트만 리턴.
        for (MainCategory v : categories) {
            MainCategoryDTO dto = new MainCategoryDTO();
            dto.setCategoryId(v.getId());
            dto.setMainCategoryName(v.getName());
            dto.setCategoryDescription(v.getDescription());
            dto.setApproval(v.getApproval());
            dto.setCreateDateTime(v.getCreateDate());
            dto.setUpdatedDatedTime(v.getUpdatedDate());
            result.add(dto);
        }

        return result;
    }

//    @Override
//    public ResponseEntity<SubCategory> createCategory(CreateSubCategoryRequest request) {
//        try {
//            SubCategory subCategory = request.toEntity();
//            //동일한 카테고리의 이름이 데이터베이스에 존재한다면 에러메시지와 함께 CONFLICT 코드를 반환합니다.
//            SubCategory existingSubCategory = subCategoryRepository.findByCategoryName(request.getCategoryName());
//            if (existingSubCategory != null) {
//                return new ResponseEntity<>(subCategory, HttpStatus.CONFLICT);
//            }
//            subCategoryRepository.save(subCategory);
//            return new ResponseEntity<>(subCategory, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//    @Override
//    @Transactional
//    public ResponseEntity<ResDto> removeCategory(int categoryId) {
//        subCategoryRepository.deleteByCategoryId(categoryId);
//        return new ResponseEntity<>(new ResDto(200, "Category remove success", null), HttpStatus.OK);
//    }
//
//    @Override
//    public ResponseEntity<SubCategory> findByCategory(int categoryId) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<Optional<?>> updateByCategory(int categoryId, String categoryName) {
//        Optional<SubCategory> target = subCategoryRepository.findByCategoryId(categoryId);
//        if(target.isPresent()) {
//            target.get().setCategoryName(categoryName);
//            subCategoryRepository.save(target.get());
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(target, HttpStatus.OK);
//    }
//
//    @Override
//    @Transactional
//    public ResponseEntity<List<?>> findAllCategory() {
//        List<SubCategory> data = subCategoryRepository.findAll();
//        return new ResponseEntity<>(data,HttpStatus.OK);
//    }

}
