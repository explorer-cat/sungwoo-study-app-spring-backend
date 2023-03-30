package testapp.demo.category.dto.mainCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testapp.demo.category.entity.MainCategory;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMainCategoryRequest {

    private Long id;
    private String name;
    private String description;

    public MainCategory toEntity() {
        return MainCategory.builder()
                .id(id)
                .name(name)
                .description(description)
                .approval(true)
                .isRemove(false)
                .createDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
    }

}

