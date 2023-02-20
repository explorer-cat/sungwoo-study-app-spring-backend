package testapp.demo.category.entity;

import antlr.collections.impl.BitSet;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder

public class Category {

    @Id
    @GeneratedValue
    private Long id;
    private int categoryId;
    private String categoryName;
    private Boolean approval;

    public Category(Long id, int categoryId, String categoryName, Boolean approval) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.approval = approval;
    }

    public Category() {

    }
}
