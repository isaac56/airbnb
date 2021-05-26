package team14.airbnb.domain.aggregate.accommodation;

import lombok.Getter;

@Getter
public enum RoomType {
    ONE_ROOM("원룸"),
    SHARE_ROOM("게스트하우스"),
    APARTMENT("아파트"),
    HOTEL("호텔");

    private String korean;

    RoomType(String korean) {
        this.korean = korean;
    }
}
