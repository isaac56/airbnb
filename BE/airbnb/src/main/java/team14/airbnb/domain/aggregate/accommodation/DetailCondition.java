package team14.airbnb.domain.aggregate.accommodation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RoomType roomType;

    private int maxOfPeople;

    private int numberOfToilet;

    private int numberOfRooms;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public DetailCondition(RoomType roomType, int maxOfPeople, int numberOfToilet, int numberOfRooms) {
        this.roomType = roomType;
        this.maxOfPeople = maxOfPeople;
        this.numberOfToilet = numberOfToilet;
        this.numberOfRooms = numberOfRooms;
    }
}
