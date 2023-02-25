package testapp.demo.category.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;

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
    @ManyToOne
    @JoinColumn(name = "main_category_ID")
    private MainCategory mainCategory;

    private String name;
    private String description;
    private Boolean approval;
    private Boolean isRemove;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public SubCategory(Long id, MainCategory mainCategory, String name, String description, Boolean approval, Boolean isRemove, LocalDateTime createDate, LocalDateTime updatedDate) {
        this.id = id;
        this.mainCategory = mainCategory;
        this.name = name;
        this.description = description;
        this.approval = approval;
        this.isRemove = isRemove;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MainCategoryResponseDTO getMainCategory() {
        MainCategoryResponseDTO mainCategoryResponseDTO = new MainCategoryResponseDTO();
        return mainCategoryResponseDTO.fromEntity(mainCategory);
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getApproval() {
        return approval;
    }

    public void setApproval(Boolean approval) {
        this.approval = approval;
    }

    public Boolean getRemove() {
        return isRemove;
    }

    public void setRemove(Boolean remove) {
        isRemove = remove;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public SubCategory() {

    }
}
