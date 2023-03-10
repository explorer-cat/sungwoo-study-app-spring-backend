package testapp.demo.category.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.bookmark.entity.SubCategoryBookMark;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@Entity
@Table(name = "subCategory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//subcategory와 양방향 맵핑 되어있어, 무한 순환 참조를 방지하기 위한 어노테이션
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "key")
public class SubCategory {
    @Id
    @GeneratedValue
    @Column(name = "sub_category_ID ")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_category_ID")
    private MainCategory mainCategory;
    private String name;
    private String description;
    @OneToMany(mappedBy = "subCategory", fetch = FetchType.LAZY)
    private List<SubCategoryBookMark> subCategoryBookMarkList = new ArrayList<>();
    private Boolean approval;
    private Boolean isRemove;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
