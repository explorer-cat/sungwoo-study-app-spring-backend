package testapp.demo.category.dto.subCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;

import java.time.LocalDateTime;

//@Getter
//@Setter

//@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SubCategoryResponseDTO {
    private long categoryId;
    private MainCategoryResponseDTO mainCategory;
    private String name;
    private String tag;
    private String description;
    private Boolean approval;
    private LocalDateTime createDateTime;
    private LocalDateTime updatedDatedTime;

    public SubCategoryResponseDTO() {

    }

    public SubCategoryResponseDTO fromEntity(SubCategory subCategory){
        return SubCategoryResponseDTO.builder()
                .categoryId(subCategory.getId())
                .mainCategory(subCategory.getMainCategory())
                .name(subCategory.getName())
                .description(subCategory.getDescription())
                .approval(subCategory.getApproval())
                .createDateTime(subCategory.getCreateDate())
                .updatedDatedTime(subCategory.getUpdatedDate()).build();
    }


}
