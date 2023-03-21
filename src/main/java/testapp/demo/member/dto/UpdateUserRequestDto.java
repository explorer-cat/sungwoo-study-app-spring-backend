package testapp.demo.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UpdateUserRequestDto {
    private String nickname;


    public UpdateUserRequestDto(String nickname) {
        this.nickname = nickname;
    }
}
