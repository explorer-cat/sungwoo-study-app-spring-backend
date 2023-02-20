package testapp.demo.category.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import testapp.demo.category.entity.Category;

@Data
@Builder
public class CreateCategoryRequest {

    @NonNull
    private int categoryId;
    @NonNull
    private String categoryName;

    @Builder
    public CreateCategoryRequest(@NonNull int categoryId, @NonNull String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Category toEntity() {
        return Category.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .approval(false).build();
    }

}

