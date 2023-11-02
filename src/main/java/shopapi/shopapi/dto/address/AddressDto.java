package shopapi.shopapi.dto.address;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDto {
    Long id;
    String addressLine;
    String latitude;
    String longitude;
}
