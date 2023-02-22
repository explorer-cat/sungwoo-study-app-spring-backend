package testapp.demo.category.dto.subCategory;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UpdateSubCategoryRequest {

    @NonNull
    private String categoryName;

}

