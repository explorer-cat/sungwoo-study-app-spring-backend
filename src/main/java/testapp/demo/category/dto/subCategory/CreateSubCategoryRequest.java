package testapp.demo.category.dto.subCategory;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.jandex.Main;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateSubCategoryRequest {

    private Long id;
    private String name;
    private MainCategory mainCategory;
    private String description;

    public SubCategory toEntity() {
        return SubCategory.builder()
                .id(id)
                .name(name)
                .mainCategory(mainCategory)
                .description(description)
                .approval(true)
                .isRemove(false)
                .createDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
    }

}

