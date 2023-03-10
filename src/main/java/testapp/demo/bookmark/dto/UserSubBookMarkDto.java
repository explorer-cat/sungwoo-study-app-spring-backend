package testapp.demo.bookmark.dto;

//package testapp.demo.member.dto;

import lombok.*;
import testapp.demo.bookmark.entity.SubCategoryBookMark;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@ToString
@Builder
public class UserSubBookMarkDto {

    private Long main_category_id;
    private String main_category_name;
    private List<UserSubBookMarkDetailDto> bookmark_sub_categories;


    public UserSubBookMarkDto(Long main_category_id, String main_category_name, List<UserSubBookMarkDetailDto> bookmark_sub_categories) {
        this.main_category_id = main_category_id;
        this.main_category_name = main_category_name;
        this.bookmark_sub_categories = bookmark_sub_categories;
    }
}
