package team14.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team14.airbnb.domain.aggregate.reservation.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
