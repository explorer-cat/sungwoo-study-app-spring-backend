package testapp.demo.category.dto.mainCategory;

import lombok.Data;
import lombok.NoArgsConstructor;
import testapp.demo.category.entity.MainCategory;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateMainCategoryRequest {

    private String name;
    private String description;

    public MainCategory toEntity() {
        return MainCategory.builder()
                .name(name)
                .description(description)
                .approval(true)
                .isRemove(false)
                .createDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
    }

}

