package testapp.demo.category.dto.subCategory;

import lombok.Data;
import lombok.NoArgsConstructor;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateSubCategoryRequest {

    private String name;
    private long mainCategoryId;
    private String description;

    public SubCategory toEntity() {
        return SubCategory.builder()
                .name(name)
                .mainCategoryId(mainCategoryId)
                .description(description)
                .approval(true)
                .isRemove(false)
                .createDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
    }

}

