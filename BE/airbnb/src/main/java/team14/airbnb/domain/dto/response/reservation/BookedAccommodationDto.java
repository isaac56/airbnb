package team14.airbnb.domain.dto.response.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team14.airbnb.domain.aggregate.reservation.Reservation;
import team14.airbnb.domain.dto.response.accommodation.AccommodationSimpleDto;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BookedAccommodationDto {
    ReservationDto reservationInfo;

    AccommodationSimpleDto accommodationInfo;

    public static BookedAccommodationDto of(Reservation reservation, Boolean wished) {
        return builder()
                .reservationInfo(ReservationDto.of(reservation))
                .accommodationInfo(AccommodationSimpleDto.of(reservation.getAccommodation(), wished))
                .build();
    }

}
