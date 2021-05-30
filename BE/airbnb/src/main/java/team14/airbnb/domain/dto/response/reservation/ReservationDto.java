package team14.airbnb.domain.dto.response.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team14.airbnb.domain.aggregate.reservation.Reservation;

import static team14.airbnb.utils.DateTimeUtils.toBasicString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ReservationDto {
    private long id;

    private String startDate;

    private String endDate;

    private int totalFee;

    private String createdTime;

    public static ReservationDto of(Reservation reservation) {
        return builder()
                .id(reservation.getId())
                .startDate(toBasicString(reservation.getStartDate()))
                .endDate(toBasicString(reservation.getEndDate()))
                .totalFee(reservation.getTotalFee())
                .createdTime(toBasicString(reservation.getCreatedTime()))
                .build();
    }
}
