package testapp.demo.category.dto.subCategory;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import testapp.demo.category.entity.SubCategory;

@Data
@Builder
public class CreateSubCategoryRequest {

    @NonNull
    private int mainCategoryId;
    @NonNull
    private String categoryName;

    @Builder
    public CreateSubCategoryRequest(@NonNull int mainCategoryId, @NonNull String categoryName) {
        this.mainCategoryId = mainCategoryId;
        this.categoryName = categoryName;
    }

//    public SubCategory toEntity() {
//        return SubCategory.builder()
//                .mainCategoryId(mainCategoryId)
//                .categoryName(categoryName)
//                .approval(false).build();
//    }

}

