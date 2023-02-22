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
@Table(name = "mainCategory")
public class MainCategory {

    @Id
    @GeneratedValue
    @Column(name = "main_category_ID")
    private Long id;
    private String name;
    private String description;
    private Boolean approval;
    private Boolean isRemove;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public MainCategory(Long id, String name, String description, Boolean approval, Boolean isRemove, LocalDateTime createDate, LocalDateTime updatedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.approval = approval;
        this.isRemove = isRemove;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
    }

    public MainCategory() {

    }
}
