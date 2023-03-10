package testapp.demo.category.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.bookmark.entity.SubCategoryBookMark;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "mainCategory")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MainCategory {

    @Id
    @GeneratedValue
    @Column(name = "main_category_ID")
    private Long id;
    private String name;
    private String description;
    private Boolean approval;
    @OneToMany(mappedBy = "mainCategory", fetch = FetchType.LAZY)
    private List<SubCategory> subCategories = new ArrayList<>();
    @OneToMany(mappedBy = "mainCategory", fetch = FetchType.LAZY)
    private List<MainCategoryBookMark> mainCategoryBookMarkList = new ArrayList<>();
    private Boolean isRemove;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;
}
