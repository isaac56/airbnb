package team14.airbnb.domain.dto.response.accommodation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.dto.response.user.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AccommodationDetailDto {
    AccommodationSimpleDto basicInfo;

    List<String> images;

    String description;

    UserDto host;

    private AccommodationDetailDto() {

    }

    public static AccommodationDetailDto of(Accommodation accommodation, boolean wished) {
        return builder()
                .basicInfo(AccommodationSimpleDto.of(accommodation, wished))
                .images(accommodation.getAccommodationImages().stream().map(x -> x.getImageUrl()).collect(Collectors.toList()))
                .description(accommodation.getDescription())
                .host(UserDto.of(accommodation.getHost()))
                .build();
    }
}
