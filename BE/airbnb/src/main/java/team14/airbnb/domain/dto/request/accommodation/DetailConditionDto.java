package team14.airbnb.domain.dto.request.accommodation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team14.airbnb.domain.aggregate.accommodation.DetailCondition;
import team14.airbnb.domain.aggregate.accommodation.RoomType;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class DetailConditionDto {
    @NotNull(message = "숙소의 방 타입은 필수입니다.(ONE_ROOM,SHARE_ROOM,APARTMENT,HOTEL")
    private RoomType roomType;

    @NotNull(message = "최대 숙박 인원은 필수입니다.")
    private Integer maxOfPeople;

    @NotNull(message = "화장실 개수는 필수입니다.")
    private Integer numberOfToilet;

    @NotNull(message = "방 개수는 필수입니다.")
    private Integer numberOfRooms;

    public DetailCondition toEntity() {
        return new DetailCondition(
                roomType,
                maxOfPeople,
                numberOfToilet,
                numberOfRooms
        );
    }
}
