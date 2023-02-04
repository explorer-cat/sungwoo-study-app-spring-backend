package testapp.demo.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.board.entity.Board;
import testapp.demo.category.entity.Category;
import testapp.demo.category.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public ResponseEntity<Category> createCategoryList() {
        List<String> sample = new ArrayList<>(Arrays.asList(new String[]{ "프론트엔드", "벡엔드", "자바(프레임워크)", "CS"}));    Category category = new Category();
        int count = 0;

        for (int i = 1; i < sample.size(); i++) {
            category.setCategoryId(count++);
            category.setCategoryName(sample.get(i));
            categoryRepository.save(category);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Category>> getCategoryList(int categoryId) {
        try{
            if(categoryId == 0) {
                return ResponseEntity.ok().body(categoryRepository.findAll());
            } else {
                return new ResponseEntity<>(categoryRepository.findByCategoryId(categoryId), HttpStatus.OK);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
