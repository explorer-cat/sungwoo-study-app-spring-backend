package testapp.demo.category.dto.mainCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Getter
//@Setter

//@NoArgsConstructor
@Builder
@Data
public class MainCategoryResponseDTO {
    private long categoryId;
    private String mainCategoryName;
    private List<Map<String,Object>> subCategories = new ArrayList<>();
    private String categoryDescription;
    private Boolean approval;
    private LocalDateTime createDateTime;
    private LocalDateTime updatedDatedTime;

    public MainCategoryResponseDTO(long categoryId, String mainCategoryName, List<Map<String, Object>> subCategories, String categoryDescription, Boolean approval, LocalDateTime createDateTime, LocalDateTime updatedDatedTime) {
        this.categoryId = categoryId;
        this.mainCategoryName = mainCategoryName;
        this.subCategories = subCategories;
        this.categoryDescription = categoryDescription;
        this.approval = approval;
        this.createDateTime = createDateTime;
        this.updatedDatedTime = updatedDatedTime;
    }

    public MainCategoryResponseDTO() {
    }

    public MainCategoryResponseDTO fromEntity(MainCategory mainCategory,List<Map<String,Object>> subCategories){
        return  MainCategoryResponseDTO.builder()
                .categoryId(mainCategory.getId())
                .subCategories(subCategories)
                .mainCategoryName(mainCategory.getName())
                .categoryDescription(mainCategory.getDescription())
                .approval(mainCategory.getApproval())
                .createDateTime(mainCategory.getCreateDate())
                .updatedDatedTime(mainCategory.getUpdatedDate()).build();
//        System.out.println("build = " + build);
//        return build;
    }


}
