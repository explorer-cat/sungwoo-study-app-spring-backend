package testapp.demo.category.dto.subCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//@Getter
//@Setter

//@NoArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubCategoryResponseDTO {
    private long sub_category_id;
    private String sub_category_name;
    private Map<String, Object> parent_category_info;
    private String tag;
    private String description;
    private Boolean approval;
    private LocalDateTime createDateTime;
    private LocalDateTime updatedDatedTime;


    public SubCategoryResponseDTO fromEntity(SubCategory subCategory){

        Map<String, Object> parent_category = new HashMap<>();
        parent_category.put("main_category_id", subCategory.getMainCategory().getId());
        parent_category.put("main_category_name",subCategory.getMainCategory().getName());

        SubCategoryResponseDTO build = SubCategoryResponseDTO.builder()
                .sub_category_id(subCategory.getId())
                .parent_category_info(parent_category)
                .sub_category_name(subCategory.getName())
                .description(subCategory.getDescription())
                .approval(subCategory.getApproval())
                .createDateTime(subCategory.getCreateDate())
                .updatedDatedTime(subCategory.getUpdatedDate()).build();

        return build;
    }


}
