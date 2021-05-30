package team14.airbnb.repository;

import java.time.LocalDate;

public interface ReservationRepositoryCustom {
    boolean canMakeReservation(long accommodationId, LocalDate startDate, LocalDate endDate);
}
