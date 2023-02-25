package testapp.demo.category.dto.subCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
    private long mainCategoryId;
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
                .mainCategoryId(subCategory.getMainCategoryId())
                .name(subCategory.getName())
                .tag(subCategory.getTag())
                .description(subCategory.getDescription())
                .approval(subCategory.getApproval())
                .createDateTime(subCategory.getCreateDate())
                .updatedDatedTime(subCategory.getUpdatedDate()).build();
    }


}
