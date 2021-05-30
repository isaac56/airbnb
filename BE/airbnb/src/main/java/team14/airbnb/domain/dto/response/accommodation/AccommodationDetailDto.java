package team14.airbnb.domain.dto.response.accommodation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.dto.response.user.UserDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class AccommodationDetailDto {
    AccommodationSimpleDto basicInfo;

    List<String> images;

    String description;

    UserDto host;

    private AccommodationDetailDto() {

    }

    public static AccommodationDetailDto of(Accommodation accommodation, Set<Long> wishSet) {
        return builder()
                .basicInfo(AccommodationSimpleDto.of(accommodation, wishSet))
                .images(accommodation.getAccommodationImages().stream().map(x -> x.getImageUrl()).collect(Collectors.toList()))
                .description(accommodation.getDescription())
                .host(UserDto.of(accommodation.getHost()))
                .build();
    }
}
