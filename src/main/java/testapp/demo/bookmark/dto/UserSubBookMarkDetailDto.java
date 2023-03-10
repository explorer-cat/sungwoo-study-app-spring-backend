package testapp.demo.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserSubBookMarkDetailDto {

    private Long sub_category_id;
    private String sub_category_name;
    private boolean selected;
    private LocalDateTime createdDate;

}
