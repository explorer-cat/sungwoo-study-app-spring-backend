package testapp.demo.category.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class CategoryVo {

    @Id
    @GeneratedValue
    private Long id;

    private int categoryId;

    private String categoryName;

}
