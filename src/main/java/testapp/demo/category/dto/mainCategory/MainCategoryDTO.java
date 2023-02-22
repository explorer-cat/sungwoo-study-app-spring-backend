package testapp.demo.category.dto.mainCategory;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class MainCategoryDTO {
    private long categoryId;
    private String mainCategoryName;
    private String categoryDescription;
    private Boolean approval;
    private LocalDateTime createDateTime;
    private LocalDateTime updatedDatedTime;

    public MainCategoryDTO(long categoryId, String mainCategoryName, String categoryDescription, Boolean approval, LocalDateTime createDateTime, LocalDateTime updatedDatedTime) {
        this.categoryId = categoryId;
        this.mainCategoryName = mainCategoryName;
        this.categoryDescription = categoryDescription;
        this.approval = approval;
        this.createDateTime = createDateTime;
        this.updatedDatedTime = updatedDatedTime;
    }

    //    public AllCategoryDataResponse(long categoryId, String mainCategoryName, String categoryDescription, Boolean approval, LocalDateTime createDateTime, LocalDateTime updatedDatedTime) {
//        this.categoryId = categoryId;
//        this.mainCategoryName = mainCategoryName;
//        this.categoryDescription = categoryDescription;
//        this.approval = approval;
//        this.createDateTime = createDateTime;
//        this.updatedDatedTime = updatedDatedTime;
//    }

}
