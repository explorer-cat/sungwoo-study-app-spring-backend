package testapp.demo.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.category.dto.CreateCategoryRequest;
import testapp.demo.category.entity.Category;
import testapp.demo.category.repository.CategoryRepository;
import testapp.demo.utils.ResDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    //entitty

//    @Override
//    public ResponseEntity createCategory(CreateCategoryRequest request) {
//        List<String> sample = new ArrayList<>(Arrays.asList(new String[]{ "프론트엔드", "벡엔드", "자바(프레임워크)", "CS"}));    Category category = new Category();
//        int count = 0;
//
//        for (int i = 1; i < sample.size(); i++) {
//            category.setCategoryId(count++);
//            category.setCategoryName(sample.get(i));
//            categoryRepository.save(category);
//        }
//
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<Category> createCategory(CreateCategoryRequest request) {
        try {
            Category category = request.toEntity();
            //동일한 카테고리의 이름이 데이터베이스에 존재한다면 에러메시지와 함께 CONFLICT 코드를 반환합니다.
            Category existingCategory = categoryRepository.findByCategoryName(request.getCategoryName());
            if (existingCategory != null) {
                System.out.printf("existingCategory = " + existingCategory);
                return new ResponseEntity<>(category, HttpStatus.CONFLICT);
            }
            categoryRepository.save(category);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResDto> getCategoryList(int categoryId) {
        try {
            return null;
//            if(categoryId == 0) {
//                return ResponseEntity.ok().body(categoryRepository.findAll());
//            } else {
//                return new ResponseEntity<>(categoryRepository.findByCategoryId(categoryId), HttpStatus.OK);
//            }
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
