package testapp.demo.category.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.board.entity.Board;
import testapp.demo.category.entity.Category;

import java.util.List;
import java.util.Map;

@Service
public interface CategoryService {
    ResponseEntity<Category> createCategoryList();

    ResponseEntity<List<Category>> getCategoryList(int categoryId);

}
