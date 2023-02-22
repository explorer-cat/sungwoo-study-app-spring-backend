package testapp.demo.category.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "subCategory")
public class SubCategory {

    @Id
    @GeneratedValue
    @Column(name = "sub_category_ID ")
    private Long id;
    @Column(name = "main_category_ID")
    private Long mainCategoryId;//부모 카테고리 아이디
    private String name;
    private String description;
    private Boolean approval;
    private Boolean isRemove;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public SubCategory(Long id, Long mainCategoryId, String name, String description, Boolean approval, Boolean isRemove, LocalDateTime createDate, LocalDateTime updatedDate) {
        this.id = id;
        this.mainCategoryId = mainCategoryId;
        this.name = name;
        this.description = description;
        this.approval = approval;
        this.isRemove = isRemove;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
    }

    public SubCategory() {

    }
}
