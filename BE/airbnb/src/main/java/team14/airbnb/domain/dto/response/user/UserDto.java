package team14.airbnb.domain.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team14.airbnb.domain.aggregate.user.User;

@Getter
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String nickName;

    private UserDto() {
    }

    public static UserDto of(User user) {
        return builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickname())
                .build();
    }
}
