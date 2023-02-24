package testapp.demo.category.dto.mainCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testapp.demo.category.entity.MainCategory;

import java.time.LocalDateTime;

//@Getter
//@Setter

//@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MainCategoryResponseDTO {
    private long categoryId;
    private String mainCategoryName;
    private String categoryDescription;
    private Boolean approval;
    private LocalDateTime createDateTime;
    private LocalDateTime updatedDatedTime;

    public MainCategoryResponseDTO() {

    }

    public MainCategoryResponseDTO fromEntity(MainCategory mainCategory){
        return MainCategoryResponseDTO.builder()
                .categoryId(mainCategory.getId())
                .mainCategoryName(mainCategory.getName())
                .categoryDescription(mainCategory.getDescription())
                .approval(mainCategory.getApproval())
                .createDateTime(mainCategory.getCreateDate())
                .updatedDatedTime(mainCategory.getUpdatedDate()).build();
    }


}
