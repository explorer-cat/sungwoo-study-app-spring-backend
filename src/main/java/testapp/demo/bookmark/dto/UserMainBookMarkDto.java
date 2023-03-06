package testapp.demo.bookmark.dto;

//package testapp.demo.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class UserMainBookMarkDto {
    private Long id;
    private String name;
    private LocalDateTime bookmark_date;


    public UserMainBookMarkDto(Long id, String name,LocalDateTime bookmark_date) {
        this.id = id;
        this.name = name;
        this.bookmark_date = bookmark_date;
    }
}
