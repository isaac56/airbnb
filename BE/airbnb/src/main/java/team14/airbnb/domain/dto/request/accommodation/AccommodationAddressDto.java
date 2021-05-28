package team14.airbnb.domain.dto.request.accommodation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team14.airbnb.domain.aggregate.accommodation.AccommodationAddress;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class AccommodationAddressDto {
    @NotEmpty(message = "숙소 주소는 필수입니다.")
    private String addressName;

    @NotEmpty(message = "숙소 도로주소는 필수입니다.")
    private String roadAddressName;

    @JsonProperty(value = "region1_depth_name")
    private String region1DepthName;

    @JsonProperty(value = "region2_depth_name")
    private String region2DepthName;

    @JsonProperty(value = "region3_depth_name")
    private String region3DepthName;

    @NotNull
    private long x;

    @NotNull
    private long y;

    public AccommodationAddress toEntity() {
        return new AccommodationAddress(
                addressName,
                roadAddressName,
                region1DepthName,
                region2DepthName,
                region3DepthName,
                x,
                y
        );
    }
}
