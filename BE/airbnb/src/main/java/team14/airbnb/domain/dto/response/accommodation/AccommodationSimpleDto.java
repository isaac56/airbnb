package team14.airbnb.domain.dto.response.accommodation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AccommodationSimpleDto {
    private long id;

    private String name;

    private int totalFee;

    private int dailyFee;

    private String addressName;

    private String roadAddressName;

    private Double x;

    private Double y;

    private Integer maxOfPeople;

    private String type;

    private Integer numberOfRoom;

    private Integer numberOfToilet;

    private List<String> options;

    private String titleImage;

    private boolean wished;

    private AccommodationSimpleDto() {
    }

    public static AccommodationSimpleDto of(Accommodation accommodation, Set<Long> wishSet) {

        return builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .totalFee(accommodation.getTotalFee())
                .dailyFee(accommodation.getDailyFee())
                .addressName(accommodation.getAddressName())
                .roadAddressName(accommodation.getRoadAddressName())
                .x(accommodation.getX())
                .y(accommodation.getY())
                .type(accommodation.getTypeName())
                .maxOfPeople(accommodation.getMaxOfPerson())
                .numberOfRoom(accommodation.getNumberOfRoom())
                .numberOfToilet(accommodation.getNumberOfToilet())
                .options(accommodation.getAccommodationOptions().stream().map(x -> x.getName()).collect(Collectors.toList()))
                .titleImage(accommodation.getTitleImageUrl())
                .wished(wishSet.contains(accommodation.getId()))
                .build();
    }
}
