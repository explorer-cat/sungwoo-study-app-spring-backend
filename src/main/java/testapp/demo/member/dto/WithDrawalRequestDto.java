package testapp.demo.member.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class WithDrawalRequestDto {
    private String reason;

    public WithDrawalRequestDto(String reason) {
        this.reason = reason;
    }
}
