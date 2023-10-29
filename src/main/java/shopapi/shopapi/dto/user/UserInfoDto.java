package shopapi.shopapi.dto.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoDto {
    String name;
    String phoneNumber;
}
